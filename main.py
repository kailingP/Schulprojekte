import os
import json
from google.cloud import dialogflow

# path to the key-file
os.environ["GOOGLE_APPLICATION_CREDENTIALS"]='hotel-booking-qigv-286f929c6f7f.json'

# example from  https://cloud.google.com/dialogflow/es/docs/quick/api#detect-intent-text-python
def detect_intent_demo(project_id, session_id, text, language_code):
    session_client = dialogflow.SessionsClient()
    session = session_client.session_path(project_id, session_id)
    text_input = dialogflow.TextInput(text=text, language_code=language_code)
    query_input = dialogflow.QueryInput(text=text_input)
    response = session_client.detect_intent(
        request={"session": session, "query_input": query_input}
    )
    print(response.query_result.fulfillment_text)
    return response.query_result


if __name__ == "__main__":
    print("How may I help you?")
    booking_obj = {}
    quit = False
    while (not quit):
        userInput = input()
        if userInput:
            response_query = detect_intent_demo('hotel-booking-qigv', 'no-session-ID', userInput, 'en')
            booking_obj.update(dict(response_query.parameters.pb))
            if response_query.diagnostic_info:
                if response_query.diagnostic_info.pb["end_conversation"]:
                    quit = True

    if response_query.all_required_params_present:
        with open("booking_result.txt", "w") as file:
            file.write(str(booking_obj))

