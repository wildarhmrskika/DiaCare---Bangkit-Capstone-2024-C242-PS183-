import json
from keras.models import load_model
from chatbotResponse import chatbot_response

# Load model
model = load_model('chatbot_model.h5')

# Load words and classes
with open('words_classes.json') as file:
    data = json.load(file)
    words = data['words']
    classes = data['classes']

# Load intents JSON
with open('diabetes_chat.json') as file:
    intents = json.load(file)

# Chatbot interaction
while True:
    user_input = input("You: ")
    if user_input.lower() == "exit":
        print("Chatbot: Goodbye!")
        break
    print("Chatbot:", chatbot_response(user_input, model, intents, words, classes))
