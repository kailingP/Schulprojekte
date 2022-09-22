import {getRankings, evaluateHand, getServerRanking, evaluateHandServer} from './game-service.js';

const gameState = {
    playerName: '',
};
const startPage = document.querySelector('#startPage');
const gamePage = document.querySelector('#gamePage');
const history = document.querySelector('#history');

function getPlayerName() {
    return document.querySelector('#playerName').value;
}

function setCountDown(countDownValue) {
    document.querySelector('#countDown').innerHTML = countDownValue;
}

function setSystemHand(systemValue) {
    document.querySelector('#systemHand').innerHTML = systemValue;
}

function prependHistoryRow(result, player, opponent) {
    const row = history.insertRow(1);
    const cell1 = row.insertCell(0);
    const cell2 = row.insertCell(1);
    const cell3 = row.insertCell(2);
    cell1.innerHTML = result;
    cell2.innerHTML = player;
    cell3.innerHTML = opponent;
}

function resetCurrentChoice() {
    setCountDown('VS');
    setSystemHand('?');
}

let playingModeLocal = true;

function generateRankList() {
    const displayRankingList = document.querySelector('#rangList');
    displayRankingList.innerHTML = 'loading';

    function displayRankList(rankingList) {
        displayRankingList.innerHTML = '';
        rankingList.slice(0, 10)
            .forEach((rank) => {
                // eslint-disable-next-line @web-and-design/wed/check-html-gen
                const li = document.createElement('li');
                li.appendChild(document.createTextNode(`Rank with ${rank.wins} wins: ${rank.players.join(', ')}`));
                displayRankingList.appendChild(li);
            });
    }

    if (!playingModeLocal) {
        getServerRanking(displayRankList);
    } else {
        getRankings(displayRankList);
    }
}

generateRankList();

document.querySelector('#switchToSever')
    .addEventListener('click', (event) => {
        playingModeLocal = !playingModeLocal;
        generateRankList();

        if (playingModeLocal) {
            event.target.innerText = 'Switch to Server';
        } else {
            event.target.innerText = 'Switch to local';
        }
    });

function disableAllChoiceButtons() {
    document.querySelectorAll('button')
        .forEach((button) => {
            button.disabled = true;
        });
}

function enableAllChoiceButtons() {
    document.querySelectorAll('button')
        .forEach((button) => {
            button.disabled = false;
        });
}

function switchToPlay() {
    gameState.playerName = getPlayerName();
    startPage.style.display = 'none';
    gamePage.style.display = 'block';
    document.querySelector('.greeting').innerHTML = `${gameState.playerName}! Choose a hand!`;
    history.innerHTML = '<tr><th>Result</th><th>Player</th><th>Against</th></tr>';
}

function switchToRanking() {
    startPage.style.display = 'block';
    gamePage.style.display = 'none';
    generateRankList();
}

document.querySelector('#startGameButton')
    .addEventListener('click', () => {
        if (getPlayerName()) {
            switchToPlay();
        }
    });

document.querySelector('#toRankingButton')
    .addEventListener('click', () => {
        switchToRanking();
    });

function timeoutHandling() {
    const TIMEOUT_DURATION = 3;

    for (let passedSeconds = 0; passedSeconds <= TIMEOUT_DURATION; passedSeconds++) {
        setTimeout(() => {
            const remainingSeconds = TIMEOUT_DURATION - passedSeconds;
            if (remainingSeconds === 0) {
                resetCurrentChoice();
                enableAllChoiceButtons();
            } else {
                setCountDown(`Next round in ${remainingSeconds}`);
            }
        }, passedSeconds * 1000);
    }
}

document.querySelector('#playOptions')
    .addEventListener('click', (event) => {
        if (event.target.localName === 'button') {
            disableAllChoiceButtons();
            const playerHand = event.target.value.toLowerCase();

            // eslint-disable-next-line no-inner-declarations
            function displaySystemHand(values) {
                setSystemHand(values.systemHand);

                timeoutHandling();

                const gameEvaluationMapping = {
                    0: 'draw',
                    1: 'lost',
                    [-1]: 'win',
                };
                const gameOutcome = gameEvaluationMapping[values.gameEval];

                prependHistoryRow(gameOutcome, values.playerHand, values.systemHand);
            }

            if (playingModeLocal) {
                evaluateHand(gameState.playerName, playerHand, displaySystemHand);
            } else {
                evaluateHandServer(gameState.playerName, playerHand, displaySystemHand);
            }
            event.stopImmediatePropagation();
        }
    });
