Yes/no guessing game. The idea is that you construct a binary tree where each leaf has the name of an object and each branch node has a
yes/no question that distinguishes between the objects. It’s like a big game of 20 questions where each question
is a yes/no question.

Each round of the game begins by you (the human player) thinking of an object. The computer will try to guess
your object by asking you a series of yes or no questions. Eventually the computer will have asked enough
questions that it thinks it knows what object you are thinking of. It will make a guess about what your object is.
If this guess is correct, the computer wins; if not, you win.

The computer keeps track of a binary tree whose nodes represent questions and answers. (Every node's data is a
string representing the text of the question or answer.) A “question” node contains a left “yes” subtree and a
right “no” subtree. An “answer” node is a leaf. The idea is that this tree can be traversed to ask the human
player a series of questions.

For example, in the tree below, the computer would begin the game by asking the player, “Is it an animal?” If
the player says “yes,” the computer goes left to the “yes” subtree and then asks the user, “Can it fly?” If the user
had instead said “no,” the computer would go right to the “no” subtree and then ask the user, “Does it have
wheels?” This pattern continues until the game reaches a leaf “answer” node. Upon reaching an answer node, the
computer asks whether that answer is the correct answer. If so, the computer wins.
