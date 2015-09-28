'''
@author: Michael

http://www.reddit.com/r/dailyprogrammer/comments/2z68di/20150316_challenge_206_easy_recurrence_relations/
'''
recurrence_relation = '*2 +1'
start = 1
end = 10

# Resursive.
def rc_rl_rec(start, op, count, end):
    if count == end: return;
    rec = eval(str(start) + op)
    print "Term %s: %s" % (count, rec)
    rc_rl_rec(rec, op, count + 1, end)
    
rc_rl_rec(start, recurrence_relation, 1, end)

# Non-recursive.
def rc_rl(start, op, end):
    for i in range(start, end):
        start = eval(str(start) + op)
        print "Term %s: %s" % (i, start)
        
rc_rl(start, recurrence_relation, end)