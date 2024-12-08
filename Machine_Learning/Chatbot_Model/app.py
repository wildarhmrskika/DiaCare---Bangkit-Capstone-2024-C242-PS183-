import streamlit as st
import json
from keras.models import load_model
from chatbotResponse import chatbot_response

# Load model dan data pendukung
@st.cache_resource  # Cache model agar tidak perlu dimuat ulang setiap saat
def load_chatbot_model():
    model = load_model('chatbot_modelV2.h5')
    return model

@st.cache_data  # Cache data JSON
def load_data():
    with open('words_classes.json') as file:
        data = json.load(file)
        words = data['words']
        classes = data['classes']
    with open('augmentedDatasetCombined.json') as file:
        intents = json.load(file)
    return words, classes, intents

# Load model dan data
model = load_chatbot_model()
words, classes, intents = load_data()

# Konfigurasi Streamlit
st.title("Chatbot Deteksi Diabetes")
st.write("Ajukan pertanyaan terkait deteksi diabetes. Ketik 'exit' untuk keluar.")

# Kotak input dan output
user_input = st.text_input("You:", "")
if user_input:
    if user_input.lower() == "exit":
        st.write("Chatbot: Goodbye!")
    else:
        response = chatbot_response(user_input, model, intents, words, classes)
        st.write(f"Chatbot: {response}")
