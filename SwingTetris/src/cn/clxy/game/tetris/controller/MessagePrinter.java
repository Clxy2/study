package cn.clxy.game.tetris.controller;

/**
 * @author clxy
 */
public interface MessagePrinter {

    void print(String msg);

    public static final MessagePrinter DefaultPrinter = new MessagePrinter() {

        @Override
        public void print(String msg) {
            System.out.println(msg);
        }
    };
}
