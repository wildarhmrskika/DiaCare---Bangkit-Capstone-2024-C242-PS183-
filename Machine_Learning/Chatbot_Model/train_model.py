#Import Library
import os
import json
import random
import numpy as np
import tensorflow as tf
import pandas as pd
import nltk
nltk.download('punkt', download_dir='Bangkit/NLTK Path')
nltk.download('punkt_tab', download_dir='Bangkit/NLTK Path')
nltk.download('wordnet', download_dir='Bangkit/NLTK Path')
nltk.data.path.append('Bangkit/NLTK Path')
from nltk.stem import WordNetLemmatizer
lemmatizer = WordNetLemmatizer()

from keras.models import Sequential
from keras.layers import Dense, Activation, Dropout
from keras.optimizers import SGD

#load intents json

data_file = open('augmentedDatasetCombined.json').read()
intents = json.loads(data_file)

words=[]
classes = []
documents = []
ignore_words = ['?', '!']

#Preprocessing Text

for intent in intents['intents']:
    for pattern in intent['patterns']:

        # take each word and tokenize it
        w = nltk.word_tokenize(pattern)
        words.extend(w)

        # adding documents
        documents.append((w, intent['tag']))

        # adding classes to our class list
        if intent['tag'] not in classes:
            classes.append(intent['tag'])

words = [lemmatizer.lemmatize(w.lower()) for w in words if w not in ignore_words]
words = sorted(list(set(words)))

classes = sorted(list(set(classes)))

print (len(documents), "documents")
print (len(words), "unique lemmatized words")
print (len(classes), "classes", classes)

#Initializing Training Data

training = []
output_empty = [0] * len(classes)

for doc in documents:
    # initializing bag of words
    bag = []

    # list of tokenized words for the pattern
    pattern_words = doc[0]

    # lemmatize each word
    pattern_words = [lemmatizer.lemmatize(word.lower()) for word in pattern_words]

    # create bag of words
    for w in words:
        bag.append(1 if w in pattern_words else 0)

    # create output row
    output_row = list(output_empty)
    output_row[classes.index(doc[1])] = 1

    # append to training data
    training.append([bag, output_row])

#mengubah words dan classes ke json file
file_path = os.path.join(os.getcwd(), 'words_classes.json')

try:
    with open(file_path, 'w') as file:
        json.dump({'words': words, 'classes': classes}, file)
        print(f"File saved successfully at {file_path}")
except Exception as e:
    print("Error saving file:", e)

# shuffle training data
random.shuffle(training)

# separate features (X) and labels (Y)
train_x = np.array([np.array(i[0], dtype=np.float32) for i in training])
train_y = np.array([np.array(i[1], dtype=np.float32) for i in training])

# Create model - 3 layers. First layer 128 neurons, second layer 128 neurons and 3rd output layer contains number of neurons
# equal to number of intents to predict output intent with softmax
model = Sequential()
model.add(Dense(128, input_shape=(len(train_x[0]),), activation='relu'))
model.add(Dropout(0.5))
model.add(Dense(128, activation='relu'))
model.add(Dropout(0.5))
model.add(Dense(len(train_y[0]), activation='softmax'))

# Compile model. Stochastic gradient descent with Nesterov accelerated gradient gives good results for this model
sgd = SGD(learning_rate=0.01, momentum=0.9, nesterov=True)
model.compile(loss='categorical_crossentropy', optimizer=sgd, metrics=['accuracy'])

#fitting and saving the model
hist = model.fit(np.array(train_x), np.array(train_y), epochs=100, batch_size=5, verbose=1)
model.save('chatbot_model.h5', hist)

print("model created")

#Function to clean user input

def clean_up_sentence(sentence):
    sentence_words = nltk.word_tokenize(sentence)
    sentence_words = [lemmatizer.lemmatize(word.lower()) for word in sentence_words]

    return sentence_words

#Function for Bag of Wrds

def bow(sentence, words, show_details=True):
    # tokenize the pattern
    sentence_words = clean_up_sentence(sentence)

    # bag of words - matrix of N words, vocabulary matrix
    bag = [0] * len(words)
    for s in sentence_words:
        for i, w in enumerate(words):
            if w == s:

                # assign 1 if current word is in the vocabulary position
                bag[i] = 1
                if show_details:
                    print ("found in bag: %s" % w)

    return(np.array(bag))

#Function for Class Prediction

def predict_class(sentence, model):
    # filter out predictions below a threshold
    p = bow(sentence, words,show_details=False)
    res = model.predict(np.array([p]))[0]
    ERROR_THRESHOLD = 0.25
    results = [[i, r] for i, r in enumerate(res) if r > ERROR_THRESHOLD]

    # sort by strength of probability
    results.sort(key=lambda x: x[1], reverse=True)
    return_list = []
    for r in results:
        return_list.append({"intent": classes[r[0]], "probability": str(r[1])})

    return return_list

#Function to get chatbot response

def getResponse(ints, intents_json):
    tag = ints[0]['intent']
    list_of_intents = intents_json['intents']
    for i in list_of_intents:
        if(i['tag']== tag):
            result = random.choice(i['responses'])
            break

    return result

#Chatbot Function

def chatbot_response(msg):
    ints = predict_class(msg, model)
    res = getResponse(ints, intents)
    return res

# Tentukan direktori tujuan
directory = "D:\\Bangkit\\ChatbotInVscode"  # Ganti dengan path tujuan Anda
file_name = "words_classes.json"

# Buat path lengkap
file_path = os.path.join(directory, file_name)

# Pastikan direktori tujuan ada
os.makedirs(directory, exist_ok=True)

# Simpan file ke direktori tersebut
try:
    with open(file_path, 'w') as file:
        json.dump({'words': words, 'classes': classes}, file)
        print(f"File saved successfully at {file_path}")
except Exception as e:
    print("Error saving file:", e)
