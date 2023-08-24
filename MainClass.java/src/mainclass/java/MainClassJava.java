/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mainclass.java;

import java.awt.FlowLayout;

import javax.swing.JFrame;

import uk.ac.leedsbeckett.oop.LBUGraphics;

import java.awt.*;

import java.io.*;

import javax.swing.*;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import java.awt.Graphics2D;


/**
 *
 * @author sameer
 */


public class MainClassJava{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
     new GraphicsSystemJava(); 
     
    }
}
class GraphicsSystemJava extends LBUGraphics{
    
    private JFileChooser choose_file;
    private File currentImageFile;
    private File currentCommandFile;
    private boolean isImageSaved;
    private boolean isCommandSaved;
    private String cmd;
    private String user_input;
    private static final String COMMAND_EXTENSION = ".txt";
    private static final String FILE_EXTENSION = ".png";
    
    
    /* This function initializes a JFrame, variables for file saving/loading, 
    a file chooser, and sets the turtle to the center of 
    the canvas pointing down with the pen down.
    */
     public  GraphicsSystemJava()
        {
                JFrame MainFrame = new JFrame("Sameer Basnet - Turtle Graphics");                
                MainFrame.setLayout(new FlowLayout());
                MainFrame.add(this);
                MainFrame.pack(); 
                MainFrame.setVisible(true); 
                
                //now display it
                
                // Initialize variables for tracking image and command saving/loading
                currentImageFile = null;
                currentCommandFile = null;
                isImageSaved = true;
                isCommandSaved = true;
                
                
                 // Initialize file chooser
                 choose_file = new JFileChooser();
                 
                loading();
                clear();
                // set turtle to the middle of the canvas and pointing down
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;
                 setxPos(centerX);
                 setyPos(centerY);


               // set pen to "down"
                 penDown();
                                                            
        }

        /* This method processes user commands entered into the LBUGraphics JTextField 
           by splitting them into words and executing the corresponding methods. 
           It also saves the input commands to a text file and displays an error message 
           if the command is not recognized.
         */
        
        public void processCommand(String command)      //this method must be provided because LBUGraphics will call it when it's JTextField is used
        {
                //String parameter is the text typed into the LBUGraphics JTextfield
                //lands here if return was pressed or "ok" JButton clicked
                //TO DO 
                
                
                 // split the command string into words
                String[] words = command.split(" ");

                 // handle the different commands
                 switch (words[0].toLowerCase()) {
                    case "reset":
                        // set turtle/pen to the middle of the canvas and point down the screen
                        reset();
                        savingCommandTotextFile(command);
                        break;
                    case "forward":
                        moving_towards_forward(words);
                        savingCommandTotextFile(command);
                        break;
                    case "backward":
                        moving_towards_backward(words);
                        savingCommandTotextFile(command);
                        break; 
                    
                    case "turnright":
                        rotating_towards_right_angle(words);
                        savingCommandTotextFile(command);
                        break;
                        
                    case "turnleft":
                        rotating_towards_left_angle(words);
                        savingCommandTotextFile(command);
                        break;
                        
                    case "penup":
                        // lift the pen up
                        penUp();
                        savingCommandTotextFile(command);
                        break;
 
                    case "pendown":
                        // put the pen down
                         penDown();
                         savingCommandTotextFile(command);
                          break;
                          
                    case "black":
                        setPenColour(Color.black); // black
                        savingCommandTotextFile(command);
                        break;
                        
                    case "green":
                        setPenColour(Color.green); // green
                        savingCommandTotextFile(command);
                        break;
                        
                    case "red":
                        setPenColour(Color.red); // red
                        savingCommandTotextFile(command);
                        break;
                        
                    case "white":
                        setPenColour(Color.white); // white
                        savingCommandTotextFile(command);
                        break;
                        
                    case "clear":
                         checkSaveStatus();
                         clear();
                         savingCommandTotextFile(command);
                         break;
                         
                    case "about":
                        about();
                        savingCommandTotextFile(command);
                        break;
                        
                    case "":
                        about();
                        savingCommandTotextFile(command);
                        break;
                    
                    case "save":
                        // Save the current image to a file
                        saveImage();
                        savingCommandTotextFile(command);
                        break;
                        
                     case "load":
                         // Load an image from a file
                         loadImage();
                         savingCommandTotextFile(command);
                         break;
                          
                    case "loadcmd":
                        // Load a command from a file
                        load_Command_File();
                        savingCommandTotextFile(command);
                        break;
                        
                    case "exit":
                        // Exit the program
                        savingCommandTotextFile(command);
                        checkSaveStatus();
                        exit_message();
                        break;
                    
                    case "square":
                        creating_a_square(words);
                        savingCommandTotextFile(command);
                        break;
                        
                    case "color":
                        creating_A_New_Colour(words);
                        savingCommandTotextFile(command);
                        break;
                        
                    case "pensize":
                        setting_the_pensize(words);
                        savingCommandTotextFile(command);
                        break;
                        
                    case "etriangle":
                        creating_a_etriangle(words);
                        savingCommandTotextFile(command);
                        break;
                        
                    case "triangle":
                        creating_A_triangle_by_side(words);
                        savingCommandTotextFile(command);
                        break;
                        
                    default:
                        error("ERROR: Invalid command.");
                        break;
                 }
        }
                 /* The error method is responsible for displaying an error 
                    message to the user. It takes a message string as input, prefixes it with "ERROR:",
                    displays the message in the LBUGraphics message area, and pops up a JOptionPane dialog 
                    box with the error message.
                 */
                 public void error(String message) {
                    displayMessage(message);
                     JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
                }
                 
                /* This method shows a dialog to save an image as a PNG file with a specified file extension. 
                   If the filename doesn't contain the extension, it's added automatically. Any error during 
                   the save process results in an error message.
                 */
                private void saveImage() {
                    // Display the file chooser dialog
                    int return_filechooser = choose_file.showSaveDialog(this);
                    
                    if (return_filechooser == JFileChooser.APPROVE_OPTION) {
                        // Get the selected file and check that it has the correct file extension
                        File picture_file = choose_file.getSelectedFile();
                        String pic_name = picture_file.getName();
                        if (!pic_name.endsWith(FILE_EXTENSION)) {
                               picture_file = new File(picture_file.getAbsolutePath() + FILE_EXTENSION);
                        }
                        // Create a BufferedImage of the current display
                         BufferedImage picture = getBufferedImage();
                         
                        // Save the image to the selected file
                        try {
                            ImageIO.write(picture, "png", picture_file);
                             isImageSaved = false;
                        }
                        catch (IOException ex) {
                             error("Unable to save file: " + ex.getMessage());
                        }
                           
                    }
                }
                
                /* This method opens a file chooser to load an image, which is set as the background 
                image if readable. Errors display an error message.
                */
                private void loadImage() {
                    // Display the file chooser dialog
                    int return_filechooser  = choose_file.showOpenDialog(this);
                    
                    if (return_filechooser == JFileChooser.APPROVE_OPTION) {
                        // Load the selected file
                        File picture_file = choose_file.getSelectedFile();
                        try {
                            BufferedImage picture = ImageIO.read(picture_file);
                            
                            // Set the background image to the loaded image
                            setBufferedImage(picture);
                           
                            
                        }
                        catch (IOException ex) {
                             error("Unable to load file: " + ex.getMessage());
                        }
                    }
                }
                


                /* This Method appends given command to file, sets flag, 
                    and displays error message if any.
                */
                private void savingCommandTotextFile(String command) {
                // append the command to the current file
                try {
                    FileWriter command_writer = new FileWriter("commands" + COMMAND_EXTENSION, true);
                    command_writer.write(command + "\n");
                    System.out.println("Command saved: "+ command);
                    command_writer.close();
                    isCommandSaved = false;
                } 
                catch (IOException e) {
                    error("Error saving command to file");
                }
                }
                

                /* This method displays file chooser, reads selected file's 
                   text, and passes the text to executing_Commands. 
                   Displays error message if any.
                */
                   private void load_Command_File() 
                   {
                    // Display the file chooser dialog
                    int return_filechooser = choose_file.showOpenDialog(this);
                    if (return_filechooser == JFileChooser.APPROVE_OPTION) {
                        // Load the selected file
                        File file_txt = choose_file.getSelectedFile();
                        try (BufferedReader txt_reader = new BufferedReader(new FileReader(file_txt))){
                            
                            StringBuilder build = new StringBuilder();
                            String sentence;
                            while ((sentence = txt_reader.readLine()) != null) {
                                build.append(sentence).append("\n");
                            }
                            txt_reader.close();
                            String cmds = build.toString();
                            executing_Commands(cmds);
                            
                        } 
                        catch (IOException ex) {
                            error("Unable to load file: " + ex.getMessage());
                        }
                    }
                }
                
                // This methods exits the program.
                private void exit() {
                    // Exit the program
                    System.exit(0);
                }
                
                /* This method processes each command of a string, ignores built-in commands, 
                   and prints a message for each executed command.
                 */
                private void executing_Commands(String cmds) {
                    // Split the commands into separate lines
                    String[] sentences = cmds.split("\n");
                    System.out.println("Command executed: "+ cmds);
                    // Process each command
                    for (String orders : sentences) {
                        String trimmedsent = orders.trim();
                        if (trimmedsent.equals("save") || trimmedsent.equals("load") || trimmedsent.equals("loadcmd") || trimmedsent.equals("clear")  || trimmedsent.equals("exit")) {
                             continue;
                        }
                        else{
                            processCommand(trimmedsent);
                        }
                        
                     }
                }
                
                // This method checks if the image or command is unsaved and prompts the user to save before proceeding.
                private void checkSaveStatus()
                {
                    if (!isImageSaved || !isCommandSaved) {
                        int response = JOptionPane.showConfirmDialog(this, "The current image/commands is not saved. Do you want to save it?", "Save Warning", JOptionPane.YES_NO_CANCEL_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            saveImage();
                        } 
                        else if (response == JOptionPane.CANCEL_OPTION) {
                             return;
                        }
                    }
                }
                
                // This method creates a square with sides of length 'lengths' using turtle graphics.
                private void create_square(int lengths)
                {
                    reset();
                    penDown();
                    forward(lengths);
                    turnRight();
                    forward(lengths);
                    turnRight();
                    forward(lengths);
                    turnRight();
                    forward(lengths);
                    reset();
                    
                }
                
                // This metgod ets the pens color to the RGB color created from the given red, green, and blue values.
                private void form_RGB_colour(int red, int green, int blue )
                {
                    Color colour = new Color(red, green,blue);
                    setPenColour(colour);
                }
                
                // This method set the pen stroke width
                public void setting_penwidth(int width) {
                    setStroke(width);
                }
                
                // This method creates an equilateral triangle of given size with Turtle graphics.
                private void create_a_equilateral(int size )
                {
                    reset();
                    penDown();
                    turnLeft(120);
                    forward(size);
                    turnLeft(120);
                    forward(size);
                    turnLeft(120);
                    forward(size);
                    reset();
                    
                }
                
                // This method creates a triangle with given sides and angles.
                private void creating_triangle(double side1, double side2, double side3) 
                {
                    Graphics form = getGraphics2DContext();
                  
    
                    double angle1 = Math.acos((side2 * side2 + side3 * side3 - side1 * side1) / (2.0 * side2 * side3));
                    double angle2 = Math.acos((side1 * side1 + side3 * side3 - side2 * side2) / (2.0 * side1 * side3));
                    double angle3 = Math.PI - angle1 - angle2;
        
                    form.setColor(getPenColour());
                    ((Graphics2D)form).setStroke(new BasicStroke((float)getStroke()));
        
        
                    int x1 = getxPos(), y1 = getyPos();
                    int x2 = x1 + (int)side1, y2 = y1;
                    int x3 = (int)(x1 + side2 * Math.cos(angle1)), y3 = (int)(y1 + side2 * Math.sin(angle1));
                    int x4 = (int)(x1 + side3 * Math.cos(angle2)), y4 = (int)(y1 - side3 * Math.sin(angle2));
                    Polygon triangle = new Polygon(new int[] {x1, x2, x3}, new int[] {y1, y2, y3}, 3);
                    form.drawPolygon(triangle);
                    repaint();
                }
    
        /*
            This method about() calls the about() method of the superclass, displays the 
             message "Sameer Basnet" using displayMessage() method and calls my_name() method.
        */
        public void about()
        {
            super.about();
            displayMessage(" Sameer Basnet");
            my_name();
            
            
            
 }
        
        /*
           This method takes an array of user input commands and moves the turtle forward. 
           It validates the input and saves the command to a text file.
        */
        private void moving_towards_forward(String [] input)
        {
            cmd = input[0];
            switch(input.length){
                case 1:
                    error("Error: You are missing parameter for " + input[0] + " Command!!!");
                    break;
                
                case 2:
                    try 
                    {
                        int movement_position = Integer.parseInt(input[1]);
                        if (movement_position <= 0) 
                        {
                            error("Error: Distance must be positive for  " + input[0] + " Command!!!");
                        }
                        else
                        {
                            forward(movement_position);
                            
                        }
                    }
                    catch (NumberFormatException e) 
                    {
                        error("Invalid parameter type for command: " + input[0]+"!!!\nParameter must be Numeric!!!");
                                   
                    }
                    break;
                    
                default:
                    error("Error: You are providing too many parameter for  " + input[0] + " Command!!!");
                    
            }
           
                        
        }
        
        // This method moves the turtle backward by a given distance.
        private void moving_towards_backward(String [] input)
        {
            cmd = input[0];
            switch(input.length){
                case 1:
                    error("Error: You are missing parameter for " + input[0] + " Command!!!");
                    break;
                
                case 2:
                    try 
                    {
                        int movement_position = Integer.parseInt(input[1]);
                        if (movement_position <= 0) 
                        {
                            error("Error: Distance must be positive for  " + input[0] + " Command!!!");
                        }
                        else
                        {
                            forward(-movement_position);
                          
                         
                        }
                    }
                    catch (NumberFormatException e) 
                    {
                        error("Invalid parameter type for command: " + input[0]+"!!!\nParameter must be Numeric!!!");
                                   
                    }
                    break;
                    
                default:
                    error("Error: You are providing too many parameter for " + input[0] + " Command!!!");
                    
            }
        }
        
        // This method is for rotating turtle towards right angle with optional input angle.
        private void rotating_towards_right_angle(String [] input)
        {
            cmd = input[0];
            switch(input.length){
           
                case 1:
                    turnRight();
                    // Save the command to a file
                    user_input = input.toString();
                    savingCommandTotextFile(user_input);
                    break;
                            
                case 2:
                    try {
                        int rotation_position = Integer.parseInt(input[1]);
                        if (rotation_position < 0 || rotation_position > 361) 
                        {
                            error("Error: Rotation angle must be in range of 0-360 for  " + input[0] + " Command!!!");
                        }
                        else
                        {
                            turnRight(rotation_position);
                        
                        }
                    }
                    catch (NumberFormatException e) 
                    {
                        error("Invalid parameter type for command: " + input[0]+"!!!\nParameter must be Numeric!!!");
                                   
                    }
                    break;
                                
                default:
                    error("Error: You are providing too many parameter " + cmd +" Command!!!");
            }           
           
                        
        }
        
        // This method is for rotating turtle towards left angle with optional input angle.
        private void rotating_towards_left_angle(String [] input)
        {
            cmd = input[0];
            switch(input.length){
           
                case 1:
                    turnLeft();
                    // Save the command to a file
                    user_input = input.toString();
                    savingCommandTotextFile(user_input);
                    break;
                            
                case 2:
                    try {
                        int rotation_position = Integer.parseInt(input[1]);
                        if (rotation_position < 0 || rotation_position > 361) 
                        {
                            error("Error: Rotation angle must be in range of 0-360 for  " + input[0] + " Command!!!");
                        }
                        else
                        {
                            turnLeft(rotation_position);
                          
                        }
                    }
                    catch (NumberFormatException e) 
                    {
                        error("Invalid parameter type for command: " + input[0]+"!!!\nParameter must be Numeric!!!");
                                   
                    }
                    break;
                                
                default:
                    error("Error: You are providing too many parameter " + cmd +" Command!!!");
                        
                       
            }

        }
        
   // This method for creating a square with input distance.
    private void creating_a_square(String [] input)
        {
            cmd = input[0];
            switch(input.length){
                case 1:
                    error("Error: You are missing parameter for " + input[0] + " Command!!!");
                    break;
                
                case 2:
                    try 
                    {
                        int movement_position = Integer.parseInt(input[1]);
                        if (movement_position <= 0) 
                        {
                            error("Error: Distance must be positive for  " + input[0] + " Command!!!");
                        }
                        else
                        {
                            create_square(movement_position);
                            
                        }
                    }
                    catch (NumberFormatException e) 
                    {
                        error("Invalid parameter type for command: " + input[0]+"!!!\nParameter must be Numeric!!!");
                                   
                    }
                    
                    break;
                    
                default:
                    error("Error: You are providing too many parameter for  " + input[0] + " Command!!!");
                    
            }
           
                        
        }
    
    // This method creates an equilateral triangle based on given distance parameter.
    private void creating_a_etriangle(String [] input)
        {
            cmd = input[0];
            switch(input.length){
                case 1:
                    error("Error: You are missing parameter for " + input[0] + " Command!!!");
                    break;
                
                case 2:
                    try 
                    {
                        int movement_position = Integer.parseInt(input[1]);
                        if (movement_position <= 0) 
                        {
                            error("Error: Distance must be positive for  " + input[0] + " Command!!!");
                        }
                        else
                        {
                            create_a_equilateral(movement_position);
                            
                        }
                    }
                    catch (NumberFormatException e) 
                    {
                        error("Invalid parameter type for command: " + input[0]+"!!!\nParameter must be Numeric!!!");
                                   
                    }
                    
                    break;
                    
                default:
                    error("Error: You are providing too many parameter for  " + input[0] + " Command!!!");
                    
            }
           
                        
        }
    
    // This method sets the pen size for drawing. Expects one parameter.
    private void setting_the_pensize(String [] input)
        {
            cmd = input[0];
            switch(input.length){
                case 1:
                    error("Error: You are missing parameter for " + input[0] + " Command!!!");
                    break;
                
                case 2:
                    try 
                    {
                        int pen_size = Integer.parseInt(input[1]);
                        if (pen_size <= 0) 
                        {
                            error("Error: Distance must be positive for  " + input[0] + " Command!!!");
                        }
                        else
                        {
                            setting_penwidth(pen_size);
                            
                        }
                    }
                    catch (NumberFormatException e) 
                    {
                        error("Invalid parameter type for command: " + input[0]+"!!!\nParameter must be Numeric!!!");
                                   
                    }
                    
                    break;
                    
                default:
                    error("Error: You are providing too many parameter for  " + input[0] + " Command!!!");
                    
            }
           
                        
        }
    
    // This method checks input parameters for RGB color and sets color.
    private void creating_A_New_Colour(String [] input)
        {
            cmd = input[0];
            switch(input.length){
                case 1:
                    error("Error: You are missing parameter for " + input[0] + " Command!!!");
                    break;
                
                case 4:
                    try 
                    {
                        int red_hex_value = Integer.parseInt(input[1]);
                        int green_hex_value = Integer.parseInt(input[2]);
                        int blue_hex_value = Integer.parseInt(input[3]);
                        if (red_hex_value < 0 || red_hex_value > 255 || green_hex_value < 0 || green_hex_value > 255 || blue_hex_value < 0 || blue_hex_value > 255) 
                        {
                            error("Error: RGB Colour Value should be in range of 0 to 255 for  " + input[0] + " Command!!!");
                        }
                        else
                        {
                            form_RGB_colour(red_hex_value, green_hex_value, blue_hex_value);
                            
                        }
                    }
                    catch (NumberFormatException e) 
                    {
                        error("Invalid parameter type for command: " + input[0]+"!!!\nParameter must be Numeric!!!");
                                   
                    }
                    
                    break;
                    
                default:
                    error("Error: You are providing too many parameter for  " + input[0] + " Command!!!");
                    
            }
           
                        
        }
    
    // This method creates a triangle with given side lengths, saves command to file. Handles errors.
    private void creating_A_triangle_by_side(String [] input)
    {
            cmd = input[0];
            switch(input.length){
                case 1:
                    error("Error: You are missing parameter for " + input[0] + " Command!!!");
                    break;
                
                case 4:
                    try 
                    {
                        double side_1_position = Double.parseDouble(input[1]);
                        double side_2_position = Double.parseDouble(input[2]);
                        double side_3_position = Double.parseDouble(input[3]);
                        if (side_1_position <= 0  || side_2_position<= 0|| side_3_position<=0 )
                        {
                            error("Error: All parameters for  " + input[0] + " Command should be positive!!!");
                        }
                        else
                        {
                            creating_triangle(side_1_position, side_2_position, side_3_position);
                            
                        }
                    }
                    catch (NumberFormatException e) 
                    {
                        error("Invalid parameter type for command: " + input[0]+"!!!\nParameter must be Numeric!!!");
                                   
                    }
                    
                    break;
                    
                default:
                    error("Error: You are providing too many parameter for  " + input[0] + " Command!!!");
                    
            }
    }
    
    private void exit_B(){
        // Move turtle to new location
        reset();
        setting_penwidth(4);
        setPenColour(Color.green);
        turnLeft(270);
        forward(150);
        turnLeft();

        penDown();
        forward(50);
        turnLeft();
        forward(50);
        turnLeft();
        forward(50);
        turnLeft();
        forward(50);
        turnRight();
        forward(50);
        turnRight();
        forward(50);
        turnRight();
        forward(50);
    }
    
    private void exit_Y()
    {
        // Move turtle to new location
        reset();
        setting_penwidth(4);
        setPenColour(Color.BLUE);
        turnLeft(270);
        forward(30);
        turnRight(225);


        penDown();
        forward(-70);
        penUp();
        forward(70);
        turnLeft();
        penDown();
        forward(70);
        penUp();
        forward(-70);
        turnRight(135);
        penDown();
        forward(50);

    }
    
    private void exit_E()
    {
        // Move turtle to new location
        reset();
        setting_penwidth(4);
        setPenColour(Color.ORANGE);
        turnRight(270);
        forward(40);
        turnLeft();
        penUp();
        forward(50);
        turnRight();

        penDown();
        turnRight();
        forward(100);
        turnLeft();
        forward(50);
        turnLeft();
        penUp();
        forward(50);
        penDown();
        turnLeft();
        forward(50);
        penUp();
        turnRight();
        forward(50);
        turnRight();
        penDown();
        forward(50);
    }
    
    private void exit_symbol()
    {
        // !
        reset();
        setting_penwidth(4);
        setPenColour(Color.RED);
        turnRight(270);
        forward(140);
        turnLeft();
        penUp();
        forward(50);
        turnRight(360);
        penDown();
        forward(-80);
        penUp();
        forward(-10);
        penDown();
        forward(-10);
        penUp();
        forward(-40);  
    }
    
    private void exit_message()
    {
        // BYE
        clear();
        exit_B();
        exit_Y();
        exit_E();
        exit_symbol();
        exit();
    }
    
    private void about_s()
    {
           reset();
           setting_penwidth(4);
           setPenColour(Color.green);
           // Move turtle to new location
           penUp();
           forward(70);
           turnRight();
           forward(250);
           
           //Draws letter "S"
           penDown();
           forward(50);
           turnLeft();
           forward(50);
           turnLeft();
           forward(50);
           turnRight();
           forward(50);
           turnRight();
           forward(50);
           penUp();
           turnLeft();
           forward(50);
           turnLeft();
           forward(100);
        
    }
    
    private void about_a()
    {
        reset();

        // Move turtle to new location
        penUp();
        forward(70);
        turnRight();
        forward(160);
        setting_penwidth(4);
        setPenColour(Color.BLUE);

        //Draws letter "A"
        penDown();
        turnLeft();
        forward(100);
        turnRight();
        penUp();
        forward(50);
        penDown();
        turnRight();
        forward(50);
        turnRight();
        forward(50);
        penUp();
        turnLeft();
        forward(50);
        turnLeft();
        penDown();
        forward(50);
        turnLeft();
        forward(50);
    }
    
    private void about_m()
    {
        // Move turtle to new location
        reset();
        penUp();
        forward(70);
        turnRight();
        forward(130);
        setting_penwidth(4);
        setPenColour(Color.ORANGE);

        //Draws letter "M"
        penDown();
        turnLeft();
        forward(100);
        penUp();
        forward(-100);
        turnLeft(45);
        penDown();
        forward(50);
        turnLeft(90);
        forward(50);
        turnLeft(225);
        forward(100);

    }
    
    private void about_first_e()
    {
       // Move turtle to new location
       reset();
       penUp();
       forward(70);
       turnRight();
       forward(30);
       setting_penwidth(4);
       setPenColour(Color.PINK);

       //Draws letter "E"
       penDown();
       turnLeft();
       forward(100);
       turnLeft();
       forward(50);
       turnLeft();
       penUp();
       forward(50);
       penDown();
       turnLeft();
       forward(50);
       penUp();
       turnRight();
       forward(50);
       turnRight();
       penDown();
       forward(50);
 
    }
    
    private void about_second_e()
    {
        // Move turtle to new location
        reset();
        penUp();
        forward(70);
        turnLeft();
        forward(60);
        setting_penwidth(4);
        setPenColour(Color.RED);

        //Draws letter "E"
        penDown();
        turnRight();
        forward(100);
        turnLeft();
        forward(50);
        turnLeft();
        penUp();
        forward(50);
        penDown();
        turnLeft();
        forward(50);
        penUp();
        turnRight();
        forward(50);
        turnRight();
        penDown();
        forward(50);

    }
    
    private void about_r()
    {
        // Move turtle to new location
        reset();
        penUp();
        forward(70);
        turnLeft();
        forward(150);
        setting_penwidth(4);
        setPenColour(Color.YELLOW);

        //Draws letter "R"
        penDown();
        turnRight();
        forward(100);
        penUp();
        forward(-100);
        turnLeft();
        penDown();
        forward(50);
        turnRight();
        forward(50);
        turnRight();
        forward(50);
        turnRight(225);
        forward(100);
        reset();
         
        
    }
    
    private void my_name()
    {
        about_s();
        about_a();
        about_m();
        about_first_e();
        about_second_e();
        about_r();
    }
    
    private void loading()
    {
        //L
            clear();
            // Move turtle to new location
            reset();
            setting_penwidth(4);
            setPenColour(Color.green);
            turnLeft(270);
            forward(300);
            turnLeft();
            forward(-50);
            
            //Draws letter "L"
            penDown();
            forward(100);
            turnLeft();
            forward(50);
            
            
            // Move turtle to new location
            reset();
            setting_penwidth(4);
            setPenColour(Color.BLUE);
            turnLeft(270);
            forward(190);
            turnLeft();
            //forward(-15);
            
            //Draws letter "O"
            penDown();
            circle(50);
            
            // Move turtle to new location
            reset();
            setting_penwidth(4);
            setPenColour(Color.MAGENTA);
            turnLeft(270);
            forward(70);
            turnLeft();
            forward(-50);
            
            
            //Draws letter "A"
            penDown();
            forward(100);
            turnRight();
            penUp();
            forward(50);
            penDown();
            turnRight();
            forward(50);
            turnRight();
            forward(50);
            penUp();
            turnLeft();
            forward(50);
            turnLeft();
            penDown();
            forward(50);
            turnLeft();
            forward(50);
            
            
            // Move turtle to new location
            reset();
            setting_penwidth(4);
            setPenColour(Color.YELLOW);
            turnLeft(270);
            forward(40);
            turnLeft();
            forward(-50);
            
            
            //Draws letter "D"
            penDown();
            forward(100);
            turnLeft();
            forward(50);
            turnLeft();
            forward(100);
            turnLeft();
            forward(50);
            
            
            // Move turtle to new location
            reset();
            setting_penwidth(4);
            setPenColour(Color.ORANGE);
            turnRight(270);
            forward(40);
            turnLeft();
            forward(-50);
            turnRight();
            
            //Draws letter "I"
            penDown();
            forward(50);
            penUp();
            forward(-25);
            turnLeft();
            penDown();
            forward(100);
            turnRight();
            penUp();
            forward(25);
            penDown();
            forward(-50);
            
            // Move turtle to new location
            reset();
            setting_penwidth(4);
            setPenColour(Color.PINK);
            turnLeft();
            forward(120);
            turnLeft();
            forward(-50);
            
            //Draws letter "N"
            penDown();
            forward(100);
            turnLeft(200);
            forward(100);
            turnRight(200);
            forward(100);
            
            // Move turtle to new location
            reset();
            setting_penwidth(4);
            setPenColour(Color.red);
            turnLeft();
            forward(180);
            turnLeft();
            forward(-50);
            
            //Draws letter "G"
            penDown();
            forward(100);
            turnRight();
            forward(50);
            turnLeft();
            forward(-20);
            penUp();
            forward(-80);
            penDown();
            forward(40);
            turnLeft();
            forward(25);
            penUp();
            forward(-25);
            turnRight();
            forward(-40);
            turnLeft();
            penDown();
            forward(50);
            
            // Move turtle to new location
            reset();
            setting_penwidth(4);
            setPenColour(Color.RED);
            turnLeft();
            forward(250);
            turnLeft();
            forward(-50);
            
            //Draws letter "."
            penDown();
            turnRight();
            forward(5);
            penUp();
            forward(10);
            penDown();
            forward(5);
            penUp();
            forward(10);
            penDown();
            forward(5);
             
            // Move turtle to new location
            reset();
            setting_penwidth(4);
            setPenColour(Color.red);
            turnLeft(280);
            forward(330);
            turnLeft();
            forward(10);
            turnRight(280);
            //Draws letter "LINE"
            penDown();
            forward(600);
                    
            
            
        
    }    
}



 
           
                        
     
    


                
                

                

        
        
