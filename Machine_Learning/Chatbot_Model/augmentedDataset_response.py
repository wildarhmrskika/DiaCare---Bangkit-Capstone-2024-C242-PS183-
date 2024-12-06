from nltk.corpus import wordnet
import random
import json

# Membaca file JSON
with open("intentsDiabetesCombined.json", "r") as file:
    dataset = json.load(file)

def synonym_replacement(sentence):
    words = sentence.split()
    new_sentence = []
    for word in words:
        synonyms = wordnet.synsets(word)
        if synonyms:
            synonym = random.choice(synonyms).lemmas()[0].name()
            new_sentence.append(synonym)
        else:
            new_sentence.append(word)
    return " ".join(new_sentence)

# Augmentasi responses
for item in dataset:
    augmented_responses = []
    for response in item["responses"]:
        augmented_responses.append(synonym_replacement(response))
    item["responses"].extend(augmented_responses)

import json

# Simpan dataset ke file baru
with open("augmentedDatasetCombined.json", "w") as file:
    json.dump(dataset, file, indent=4)

print("Dataset augmentasi berhasil disimpan!")

