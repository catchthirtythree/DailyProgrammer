import Tkinter

'''
@author: Michael

http://www.reddit.com/r/dailyprogrammer/comments/2ww3pl/2015223_challenge_203_easy_the_start_of_something/
'''

master = Tkinter.Tk()

w_width = 400
w_height = 400

w = Tkinter.Canvas(master, width=w_width, height=w_height)
w.pack()

w.create_rectangle(w_width * 1 / 4, w_height * 1 / 4, w_width * 3 / 4, w_height * 3 / 4, fill = 'black')

w.mainloop()