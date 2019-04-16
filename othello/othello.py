'''
Class: CPSC 427
Team Member 1: Nathan Magrogan
Team Member 2: none
Submitted By Nathan Magrogan
GU Username: nmagrogan
File Name: othello.py
code for an othello game, lets playes go back and fourth
making moves in othello
'''

#some constants
VALID_COLUMN = ["A","B","C","D","E","F","G","H"]
VALID_ROW = [1,2,3,4,5,6,7,8]


class Othello:
    #creates a starting board
    def __init__(self):
        self.board = [[" ","A","B","C","D","E","F","G","H"],
                      ["1","0","0","0","0","0","0","0","0"],
                      ["2","0","0","0","0","0","0","0","0"],
                      ["3","0","0","0","0","0","0","0","0"],
                      ["4","0","0","0","B","W","0","0","0"],
                      ["5","0","0","0","W","B","0","0","0"],
                      ["6","0","0","0","0","0","0","0","0"],
                      ["7","0","0","0","0","0","0","0","0"],
                      ["8","0","0","0","0","0","0","0","0"],]
        #score where 0 is score for black and 1 is score for white
        self.score = [2,2]

    #displays the board
    def display_board(self):
            for row in self.board:
                print(row)
            print "Score B = " + str(self.score[0])
            print "Score W = " + str(self.score[1])
            print("")

    #asks for user input for a new pos to place a tile
    def input_pos(self,player_name):
        letter_pos = raw_input(player_name + " input column letter: " )
        while letter_pos not in VALID_COLUMN:
            letter_pos = raw_input("Invald only letters A-H: ")

        number_pos = input(player_name + " input row number: ")
        while number_pos not in VALID_ROW:
            number_pos = input("Invald only numbers 1-8: ")

        return letter_pos, number_pos

    #returns all adjacent tiles given a positon
    def get_adj_tiles(self,number_pos,letter_pos_int):


        if number_pos == 8 and letter_pos_int == 8:
            adjacent_tiles = ["edge", #below
                              self.board[number_pos-1][letter_pos_int], #above
                              "edge", #right
                              self.board[number_pos][letter_pos_int-1]] #left
        elif letter_pos_int == 8:
            adjacent_tiles = [self.board[number_pos+1][letter_pos_int], #below
                              self.board[number_pos-1][letter_pos_int], #above
                              "edge", #right
                              self.board[number_pos][letter_pos_int-1]] #left
        elif number_pos == 8:
            adjacent_tiles = ["edge", #below
                              self.board[number_pos-1][letter_pos_int], #above
                              self.board[number_pos][letter_pos_int+1], #right
                              self.board[number_pos][letter_pos_int-1]] #left
        else:
            adjacent_tiles = [self.board[number_pos+1][letter_pos_int], #below
                              self.board[number_pos-1][letter_pos_int], #above
                              self.board[number_pos][letter_pos_int+1], #right
                              self.board[number_pos][letter_pos_int-1]] #left


        return adjacent_tiles


    #generates next possible moves, returns a list of tuples
    def generate_next_moves(self,player_name):
        next_moves = []
        if player_name == "B":
            opposte_name = "W"
        else:
            opposte_name = "B"


        for i in range(1,9,1): #number
            for j in range(1,9,1): #letter
                if self.board[i][j] == "0":
                    adjacent_tiles = self.get_adj_tiles(i,j)
                    for k in range(len(adjacent_tiles)):
                        if adjacent_tiles[k] == opposte_name:
                            if k == 0:
                                for l in range(i+1,9,1):
                                    if self.board[l][j] == player_name:
                                        next_moves.append((i,j))
                                        break
                            elif k == 1:
                                for l in range(i-1,0,-1):
                                    if self.board[l][j] == player_name:
                                        next_moves.append((i,j))
                                        break
                            elif k == 2:
                                for l in range(j+1,9,1):
                                    if self.board[i][l] == player_name:
                                        next_moves.append((i,j))
                                        break
                            elif k == 3:
                                for l in range(j-1,0,-1):
                                    if self.board[i][l] == player_name:
                                        next_moves.append((i,j))
                                        break
        return next_moves


    def flip_tiles(self,number_pos,letter_pos,player_name):
        if player_name == "B":
            opposite_name = "W"
        else:
            opposite_name = "B"

        adjacent_tiles = self.get_adj_tiles(number_pos,letter_pos)
        for i in range(len(adjacent_tiles)):
            if adjacent_tiles[i] == opposite_name:
                if i == 0:
                    for l in range(number_pos+1,9,1):
                        if self.board[l][letter_pos] == player_name:
                            if l == 8 or self.board[l+1][letter_pos] == "0":
                                for j in range(l,number_pos,-1):
                                    if self.board[j][letter_pos] == opposite_name:
                                        self.board[j][letter_pos] = player_name
                                        if player_name == "B":
                                            self.score[0] = self.score[0]+1
                                            self.score[1] = self.score[1]-1
                                        else:
                                            self.score[1] = self.score[1]+1
                                            self.score[0] = self.score[0]-1

                elif i == 1:
                    for l in range(number_pos-1,0,-1):
                        if self.board[l][letter_pos] == player_name:
                            if l == 1 or self.board[l-1][letter_pos] == "0":
                                for j in range(l,number_pos,1):
                                    if self.board[j][letter_pos] == opposite_name:
                                        self.board[j][letter_pos] = player_name
                                        if player_name == "B":
                                            self.score[0] = self.score[0]+1
                                            self.score[1] = self.score[1]-1
                                        else:
                                            self.score[1] = self.score[1]+1
                                            self.score[0] = self.score[0]-1

                elif i == 2:
                    for l in range(letter_pos+1,9,1):
                        if self.board[number_pos][l] == player_name:
                            if l == 8 or self.board[number_pos][l+1] == "0":
                                for j in range(l,letter_pos,-1):
                                    if self.board[number_pos][j] == opposite_name:
                                        self.board[number_pos][j] = player_name
                                        if player_name == "B":
                                            self.score[0] = self.score[0]+1
                                            self.score[1] = self.score[1]-1
                                        else:
                                            self.score[1] = self.score[1]+1
                                            self.score[0] = self.score[0]-1

                elif i == 3:
                    for l in range(letter_pos-1,0,-1):
                        if self.board[number_pos][l] == player_name:
                            if l == 1 or self.board[number_pos][l-1] == "0":
                                for j in range(l,letter_pos,1):
                                    if self.board[number_pos][j] == opposite_name:
                                        self.board[number_pos][j] = player_name
                                        if player_name == "B":
                                            self.score[0] = self.score[0]+1
                                            self.score[1] = self.score[1]-1
                                        else:
                                            self.score[1] = self.score[1]+1
                                            self.score[0] = self.score[0]-1





    #runs through a single turn for a player
    def player_turn(self,player_name):
        #initial input
        letter_pos, number_pos = self.input_pos(player_name)
        letter_pos_int = VALID_COLUMN.index(letter_pos)+1

        #checks if initial input was in a valid position, if not it wil
        #request a new input till a valid one is given
        legal_moves = self.generate_next_moves(player_name)

        move = (number_pos,letter_pos_int)

        while move not in legal_moves:
            print "Invalid move, try agian"
            letter_pos,number_pos = self.input_pos(player_name)
            letter_pos_int = VALID_COLUMN.index(letter_pos)+1
            move = (number_pos,letter_pos_int)




        if player_name == "B":
            self.score[0] = self.score[0]+1
        else:
            self.score[1] = self.score[1]+1

        #puts tile on board
        self.board[number_pos][VALID_COLUMN.index(letter_pos)+1] = player_name
        #flips whatever other tiles need to be flipped and changes score
        self.flip_tiles(number_pos,letter_pos_int,player_name)



def main():

    game = Othello()
    game.display_board()
    game.player_turn("B")
    game.display_board()
    game.player_turn("W")
    game.display_board()



main()
