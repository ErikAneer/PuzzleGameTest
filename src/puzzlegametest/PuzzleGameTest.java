
package puzzlegametest;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import static java.nio.file.Files.list;
import static java.rmi.Naming.list;
import java.util.Arrays;
import static java.util.Collections.list;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PuzzleGameTest extends JFrame {

         JPanel panel = new JPanel();
         
         JButton b1 = new JButton("1");
         JButton b2 = new JButton("2");
         JButton b3 = new JButton("3");
         JButton b4 = new JButton("4");
         JButton b5 = new JButton("5");
         JButton b6 = new JButton("6");
         JButton b7= new JButton("7");
         JButton b8 = new JButton("8");
         JLabel l = new JLabel();
          int rows = 3;
         JComponent[][] list = new JComponent[][] 
         { {b1, b2, b3},
            {b4, b5, b6},
            {b7, b8, l},
         };
         
    
         BufferedImage source;
         BufferedImage resized;    
         Image image;
         int width, height;    
    


    private final int NUMBER_OF_BUTTONS = 9;
    private final int DESIRED_WIDTH = 300;

        
        
        PuzzleGameTest(){
                panel.setLayout(new GridLayout(rows,rows));
                panel.setBackground(Color.BLACK);
                panel.setSize(250, 300);
                panel.add( list [0][0] = b1);
                panel.add( list [0][1] = b2);
                panel.add( list [0][2] = b3);
                panel.add( list [1][0] = b4);
                panel.add( list [1][1] = b5);
                panel.add( list [1][2] = b6);
                panel.add( list [2][0] = b7);
                panel.add( list [2][1] = b8);
                panel.add( list [2][2] = l);
                
                this.add(panel);
                this.setSize(280, 220);
                setLocation(800,300); 
                this.setVisible(true);
                this.setDefaultCloseOperation(EXIT_ON_CLOSE);
                
                  try {
                            source = loadImage();
                            int h = getNewHeight(source.getWidth(), source.getHeight());
                            resized = resizeImage(source, DESIRED_WIDTH, h,
                                    BufferedImage.TYPE_INT_ARGB);

                  } catch (IOException ex) {
                          JOptionPane.showMessageDialog(this, "Could not load image", "Error", 
                                    JOptionPane.ERROR_MESSAGE);
                 }

                 width = resized.getWidth(null);
                 height = resized.getHeight(null);
                 
                 Image[][] imagearray = new Image[3] [3];
                 
                 for (int i = 0; i < rows; i++) {
                          for (int j = 0; j < rows; j++) {
                               
                                image = createImage(new FilteredImageSource(resized.getSource(),
                                        new CropImageFilter(j * width / rows, i * height / rows,
                                                (width / rows), height / rows)));
                               
                                imagearray[i][j]= image;
                          }
                 }
 b1.setIcon(new ImageIcon(imagearray[0][0]));
  b2.setIcon(new ImageIcon(imagearray[0][1]));
   b3.setIcon(new ImageIcon(imagearray[0][2]));
    b4.setIcon(new ImageIcon(imagearray[1][0]));
     b5.setIcon(new ImageIcon(imagearray[1][1]));
      b6.setIcon(new ImageIcon(imagearray[1][2]));
       b7.setIcon(new ImageIcon(imagearray[2][0]));
        b8.setIcon(new ImageIcon(imagearray[2][1]));
                
        
                b1.addActionListener((ActionEvent e)-> {
                    moveBrick(rows, b1, list, e, l);
                });
                b2.addActionListener((ActionEvent e)-> {
                    moveBrick(rows, b2, list, e, l);
                });
                b3.addActionListener((ActionEvent e)-> {
                    moveBrick(rows, b3, list, e, l);
                });
                b4.addActionListener((ActionEvent e)-> {
                    moveBrick(rows, b4, list, e, l);
                });
                b5.addActionListener((ActionEvent e)-> {
                    moveBrick(rows, b5, list, e, l);
                });
                b6.addActionListener((ActionEvent e)-> {
                    moveBrick(rows, b6, list, e, l);
                });
                b7.addActionListener((ActionEvent e)-> {
                    moveBrick(rows, b7, list, e, l);
                });
                b8.addActionListener((ActionEvent e)-> {
                    moveBrick(rows, b8, list, e, l);
                });
                
        }
        
         public int getNewHeight(int w, int h) {

        double ratio = DESIRED_WIDTH / (double) w;
        int newHeight = (int) (h * ratio);
        return newHeight;
    }

    public BufferedImage loadImage() throws IOException {

        BufferedImage bimg = ImageIO.read(new File("src/puzzlegametest/jawas.jpg"));

        return bimg;
    }

    public BufferedImage resizeImage(BufferedImage originalImage, int width,
            int height, int type) throws IOException {

        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();

        return resizedImage;
    }
    

         public void moveBrick(int rows, JComponent pressedButton, JComponent[][] buttonList, ActionEvent e, JLabel empty) {
                  
                 if (e.getSource() == pressedButton) {
                 int rowOfButton =0, columnOfButton =0;

                 for(int i = 0; i< rows; i++) {
                          for (int j = 0; j< rows; j++) {
                                   if (buttonList [i][j].equals(pressedButton)){
                                           rowOfButton = i;
                                           columnOfButton = j;
                                   }
                          }        
                 }
                 for (int k = (rows-1); k > 0; k-- ) {
                          if (rowOfButton + k <=  rows-1 && buttonList[rowOfButton + k] [columnOfButton] == empty ) {
                                  swapBricks(buttonList, buttonList [rowOfButton + (k-1)] [columnOfButton], buttonList [rowOfButton + k] [columnOfButton] );
                          }
                 }
                 for (int k = (rows-1); k > 0; k-- ) {
                          if (rowOfButton - k >=  0 && buttonList[rowOfButton - k] [columnOfButton] == empty ) {
                                   swapBricks(buttonList, buttonList [rowOfButton - (k-1)] [columnOfButton], buttonList [rowOfButton - k] [columnOfButton] );
                          }
                 }
                 for (int k = (rows-1); k > 0; k-- ) {
                          if (columnOfButton + k <=  rows-1 && buttonList[rowOfButton ] [columnOfButton + k] == empty ) {
                                  swapBricks(buttonList, buttonList [rowOfButton] [columnOfButton +  (k-1)], buttonList[rowOfButton] [columnOfButton + k] );
                          }
                 }
                 for (int k = (rows-1); k > 0; k-- ) {
                          if (columnOfButton - k >=  0 && buttonList[rowOfButton ] [columnOfButton - k] == empty ) {
                                   swapBricks(buttonList, buttonList [rowOfButton] [columnOfButton -  (k-1)], buttonList[rowOfButton] [columnOfButton - k] );
                                   }
                          }
                  }
                 Container c1 =((Container)e.getSource()) .getParent();
                                 c1.revalidate();
                                 c1.repaint();
                                  for(int i = 0; i< rows; i++) {
                                           for (int j = 0; j< rows; j++) {
                                                    c1.add(buttonList [i][j]);
                                           }
                                   }               
         } // end method moveBrick
         
         public void swapBricks(JComponent[][] buttonList, JComponent first, JComponent second) {
                 JComponent temp;
                  temp= first;
                 first = second;
                 second = temp;
         } // end method swapBricks
     
        public static void main(String[] args) {
                

            PuzzleGameTest p = new PuzzleGameTest();
        }

}
