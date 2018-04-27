// This is a game of Tic Tac Toe
// Written by Jacqui Dilger - 25/04/2018

import java.awt.*;
import java.applet.Applet;
import java.awt.event.*;

public class TicTacToe extends Applet implements MouseListener
{
    static char PLAYER_1 = 'X';
    static char PLAYER_2 = 'O';
    static char EMPTY = 'N';
    
    Board board;
    char CURRENT_PLAYER;
    char GAME_STATUS;
    
    public void init()
    {
        board = new Board();
        CURRENT_PLAYER = PLAYER_1;
        GAME_STATUS = EMPTY;
        addMouseListener(this);
    }
    
    public void paint(Graphics g)
    {
        // Big text
        g.setFont(new Font("TimesRoman", Font.PLAIN, 32));
        
        // Draw lines starting at 50,50
        g.drawLine (150, 50, 150, 350);
        g.drawLine (250, 50, 250, 350);
        g.drawLine (50, 150, 350, 150);
        g.drawLine (50, 250, 350, 250);
        
        // Print board symbols in squares
        for (int x = 0; x < board.cols; x++)
        {
            for (int y = 0; y < board.rows; y++)
            {
                int topX = 90 + x * 100;
                int topY = 110 + y * 100;
                char temp = board.board[x][y];
                
                if (temp == EMPTY) continue;
                else if (temp == PLAYER_1) g.setColor(new Color(255, 0, 0));
                else if (temp == PLAYER_2) g.setColor(new Color(30, 144, 255));
                g.drawString (temp + "", topX, topY);
            }
        }
        
        // Print status
        g.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        
        g.setColor(Color.BLACK);
        if (GAME_STATUS == PLAYER_1)
            g.drawString ("X is the winner!", 50, 400);
        else if (GAME_STATUS == PLAYER_2)
            g.drawString ("O is the winner!", 50, 400);
        else if (GAME_STATUS == 'T')
            g.drawString ("It's a tie!", 50, 400);
    }
    
    public void mousePressed(MouseEvent e)
    {
        if (GAME_STATUS != EMPTY) return;
        
        int x = 0;
        for (int i = e.getX() - 50; i > 100; i -= 100) x++;
        
        int y = 0;
        for (int i = e.getY() - 50; i > 100; i -= 100) y++;
        
        if (!board.isValidMove(x, y)) return;
        
        board.placeMove(x, y, CURRENT_PLAYER);
        swap();
        GAME_STATUS = board.getWinner();
        
        repaint();
    }
    
    public void swap() 
    {
        if (CURRENT_PLAYER == PLAYER_1)
            CURRENT_PLAYER = PLAYER_2;
        else CURRENT_PLAYER = PLAYER_1;
    }
    
    private class Board
    {
        int rows;
        int cols;
        char[][] board;
        
        Board()
        {
            rows = 3;
            cols = 3;
            board = new char[cols][rows];
            for (int x = 0; x < cols; x++)
            {
                for (int y = 0; y < rows; y++)
                {
                    board[x][y] = EMPTY;
                }
            }
        }
        
        boolean isValidMove(int x, int y)
        {
            if(x >= cols || y >= rows) return false;
            return board[x][y] == EMPTY;
        }
        
        void placeMove(int x, int y, char player)
        {
            board[x][y] = player;
        }
        
        char getWinner()
        {
            // Check for win in rows
            for (int y = 0; y < rows; y++)
            {
                char temp = board[0][y];
                if (temp == EMPTY) continue;
                for (int x = 1; x < cols; x++)
                {
                    if (temp == board[x][y]) 
                    {
                        if (x + 1 == cols) return temp;
                        continue;
                    }
                    else break;
                }
                
            }
            
            //Check for win in columns
            for (int x = 0; x < cols; x++)
            {
                char temp = board[x][0];
                if (temp == EMPTY) continue;
                for (int y = 1; y < rows; y++)
                {
                    if (temp == board[x][y])
                    {
                        if (y + 1 == rows) return temp;
                        continue;
                    }
                    else break;
                }
                
            }
            
            //Check for win in diagonal
            char temp = board[0][0];
            if (temp != EMPTY && temp == board[1][1] && temp == board[2][2]) return temp;
            temp = board[2][0];
            if (temp != EMPTY && temp == board[1][1] && temp == board[0][2]) return temp;
            
            if (isFull()) return 'T';
            else return EMPTY;
        }
        
        boolean isFull()
        {
            for (int x = 0; x < cols; x++)
            {
                for (int y = 0; y < rows; y++)
                {
                    if (board[x][y] == EMPTY) return false;
                }
            }
            return true;
        }
    }
    
    // Unused events
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
}