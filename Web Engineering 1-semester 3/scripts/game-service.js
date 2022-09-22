const DELAY_MS = 1000;
const playerStats = {
  Markus: {
    user: 'Markus',
    win: 5,
    lost: 6,
  },
  Michael: {
    user: 'Michael',
    win: 4,
    lost: 5,
  },
  Kailing: {
    user: 'Kailing',
    win: 0,
    lost: 0,
  },
};

const englishToGermanHand = {
  scissors: 'Schere',
  stone: 'Stein',
  paper: 'Papier',
};

function getRankingsFromPlayerStats(playResults) {
  const pointDistribution = {};

  const playerList = Object.values(playResults);

  playerList.forEach((player) => {
    if (!Array.isArray(pointDistribution[player.win])) {
      pointDistribution[player.win] = [];
    }
    pointDistribution[player.win].push(player.user);
  });

  const keys = Object.keys(pointDistribution);
  keys.sort((a, b) => b - a);

  const rankingList = [];
  for (let i = 0; i < keys.length; i++) {
    rankingList.push({
      wins: keys[i],
      players: pointDistribution[keys[i]],
    });
  }

  return rankingList;
}

export const HANDS = ['scissors', 'stone', 'paper'];

export function getRankings(rankingsCallbackHandlerFn) {
  const rankingsArray = getRankingsFromPlayerStats(playerStats);
  setTimeout(() => rankingsCallbackHandlerFn(rankingsArray), DELAY_MS);
}

async function getServerData() {
  const serverDate = await fetch('https://stone.dev.ifs.hsr.ch/ranking');
  return serverDate.json();
}

async function serverPlay(playerName, playerHand) {
  const serverChoice = await fetch(`https://stone.dev.ifs.hsr.ch/play?playerName=${playerName}&playerHand=${englishToGermanHand[playerHand]}&mode=normal`);
  return serverChoice.json();
}

export function getServerRanking(rankingsCallbackHandlerFn) {
  getServerData().then((data) => {
    const serverRankingArray = getRankingsFromPlayerStats(data);
    setTimeout(() => rankingsCallbackHandlerFn(serverRankingArray), DELAY_MS);
  });
}

const evalLookup = {
  scissors: {
    scissors: 0,
    stone: 1,
    paper: -1,
  },
  stone: {
    scissors: -1,
    stone: 0,
    paper: 1,
  },
  paper: {
    scissors: 1,
    stone: -1,
    paper: 0,
  },
};

function getGameEval(playerHand, systemHand) {
  return evalLookup[playerHand][systemHand];
}

function updateRanking(playerName, gameEval) {
  // eslint-disable-next-line @web-and-design/wed/use-action-map
  if (playerStats[playerName] === undefined) {
    playerStats[playerName] = {
      user: playerName,
      win: 0,
      lost: 0,
    };
  }

  if (gameEval === 1) {
    playerStats[playerName].lost += 1;
  }

  if (gameEval === -1) {
    playerStats[playerName].win += 1;
  }
}
export function evaluateHandServer(playerName, playerHand, gameRecordHandlerCallbackFn) {
  serverPlay(playerName, playerHand).then((serverValues) => {
    const choiceMapping = {
      Stein: 'stone',
      Schere: 'scissors',
      Papier: 'paper',
    };

    const systemHand = choiceMapping[serverValues.choice];

    const gameWinMapping = {
      true: -1,
      false: 1,
      undefined: 0,
    };

    const gameEval = gameWinMapping[serverValues.win];

    setTimeout(() => gameRecordHandlerCallbackFn({
      playerHand,
      systemHand,
      gameEval,
    }), DELAY_MS);
  });
}

export function evaluateHand(playerName, playerHand, gameRecordHandlerCallbackFn) {
  const systemHand = HANDS[Math.floor(Math.random() * 3)];
  const gameEval = getGameEval(playerHand, systemHand);

  updateRanking(playerName, gameEval);

  setTimeout(() => gameRecordHandlerCallbackFn({
    playerHand,
    systemHand,
    gameEval,
  }), DELAY_MS);
}
