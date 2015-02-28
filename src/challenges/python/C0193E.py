"""
@author Michael

http://www.reddit.com/r/dailyprogrammer/comments/2ptrmp/20141219_challenge_193_easy_acronym_expander/
"""
import sys

dictionary = {
    "lol": "laugh out loud",
    "dw": "don't worry",
    "hf": "have fun",
    "gg": "good game",
    "brb": "be right back",
    "g2g": "got to go",
    "wtf": "what the fuck",
    "wp": "well played",
    "gl": "good luck",
    "imo": "in my opinion"
}

phrase = ' '.join(sys.argv[1:])

for (k, v) in dictionary.items():
    phrase = phrase.replace(k, v)
        
print phrase