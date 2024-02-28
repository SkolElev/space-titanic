package spacetitanic;

import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    GamePanel gamePanel;

    /* Keyboard buttons */
    private int NUM_KEYS = 255;
    private boolean[] keys = new boolean[NUM_KEYS];
    private boolean[] keysLast = new boolean[NUM_KEYS];

    /* Mouse buttons */
    private int NUM_BUTTONS = 10;
    private boolean[] buttons = new boolean[NUM_BUTTONS];
    private boolean[] buttonsLast = new boolean[NUM_BUTTONS];

    private int mouseX, mouseY, scroll;

    public Input(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        mouseX = 0;
        mouseY = 0;
        scroll = 0;

        gamePanel.addKeyListener(this);
        gamePanel.addMouseListener(this);
        gamePanel.addMouseMotionListener(this);
        gamePanel.addMouseWheelListener(this);
    }

    /* Every game tick we update the lastArray with the values from the current array. */
    public void update() {
        scroll = 0;
        System.arraycopy(keys, 0, keysLast, 0, NUM_KEYS);
        System.arraycopy(buttons, 0, buttonsLast, 0, NUM_BUTTONS);
    }

    /* Check: If a key is being pressed down right now.
     * Parameter: keyCode of key being pressed.
     * Return: True if pressed, else false */
    public boolean isKey(int keyCode) {
        return keys[keyCode];
    }

    /* Check: If a key is being pressed down right now.
     * Parameter: keyCode of key being pressed.
     * Return: True if key pressed and not from last tick, else false */
    public boolean isKeyDown(int keyCode) {
        return keys[keyCode] && !keysLast[keyCode];
    }

    /* Check: If a key is being released.
     * Parameter: keyCode of key being released.
     * Return: True if key released and not from last tick, else false */
    public boolean isKeyUp(int keyCode) {
        return !keys[keyCode] && keysLast[keyCode];
    }

    /* Check: If a button is being pressed down right now.
     * Parameter: keyCode of key being pressed.
     * Return: True if pressed, else false */
    public boolean isButton(int button) {
        return buttons[button];
    }

    /* Check: If a button is being pressed down right now.
     * Parameter: keyCode of button being pressed.
     * Return: True if button pressed and not from last tick, else false */
    public boolean isButtonDown(int button) {
        return buttons[button] && !buttonsLast[button];
    }

    /* Check: If a button is being released.
     * Parameter: keyCode of button being released.
     * Return: True if button released and not from last tick, else false */
    public boolean isButtonUp(int button) {
        return !buttons[button] && buttonsLast[button];
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public int getScroll() {
        return scroll;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        buttons[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buttons[e.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        /* Temporary code. Scale might be removed at a later time. */
        mouseX = (int) (e.getX());
        mouseY = (int) (e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        /* Temporary code. Scale might be removed at a later time. */
        mouseX = (int) (e.getX());
        mouseY = (int) (e.getY());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll = e.getWheelRotation();
    }

}

