"""
@author: Michael

http://www.reddit.com/r/dailyprogrammer/comments/2vc5xq/20150209_challenge_201_easy_counting_the_days/
"""
import datetime, sys

a = datetime.datetime.today()
b = datetime.datetime.strptime('-'.join(sys.argv[1:]), '%Y-%m-%d')

print 'There are %d days between %s and %s.' % ((b - a).days, b, a)