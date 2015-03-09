'''
Created on Mar 5, 2015

@author: Michael
'''
import re, sys

# Get the whole text.
macbeth = re.sub('\[[\w .,&\']+\]', '', open('..\..\..\ext\C0204E', 'r').read()) # The ugliest file path ever.

# index will be in the form:
# index {
#   act : {
#       scene : [(speaker, dialogue), ...]
#       ... }
#   ... }
index = {}

# Regex parsing scripts.
re_act      = re.compile('(ACT [IV.]+)(.*?)(?=ACT|\Z)', flags = re.S)
re_scene    = re.compile('(SCENE [IV.]+ [\w .\']+)(.*?)(?=SCENE|\Z)', flags = re.S)
re_dialogue = re.compile('(  [A-Z .]+\n)(    .*?\n)(?=[|\n\n|\Z])', flags = re.S)

# Parse scenes from acts.
re_scenes = []
for (act, dialogue) in re_act.findall(macbeth):
    re_scenes.append((act, re_scene.findall(dialogue)))

# Parse dialogues from scenes.
for (act, scenes) in re_scenes:
    temp = {}
    index[act] = {}
    for (scene, dialogue) in scenes:
        temp[scene] = re_dialogue.findall(dialogue.strip('\n'))
        index[act].update(temp)

search = ' '.join(sys.argv[1:])

for (act, scenes) in index.items():
    for (scene, dialogue) in scenes.items():
        for (speaker, line) in dialogue:
            if search in line:
                print act, scene, '\n', speaker, line