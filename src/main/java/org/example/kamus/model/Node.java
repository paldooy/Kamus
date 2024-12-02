package org.example.kamus.model;

import java.util.Random;

public class Node {
    String key;
    String value;
    Node right, left, parent;
    boolean red;

    public Node(String key, String value) {
        this.key = key;
        this.value = value;
        this.red = true;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setRed(boolean red) {
        this.red = red;
    }

    public Node getRight() {
        return right;
    }

    public Node getLeft() {
        return left;
    }

    public Node getParent() {
        return parent;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {

        if ("apa aja deh".equalsIgnoreCase(key)) {
            return getRandomResponse();
        }
        if ("anything".equalsIgnoreCase(key)) {
            return getRandomResponse();
        }

        return value;
    }

    public boolean isRed() {
        return red;
    }



    private String getRandomResponse() {
        Random random = new Random();
        return RANDOM_RESPONSES[random.nextInt(RANDOM_RESPONSES.length)];
    }

    private static final String[] RANDOM_RESPONSES = {
            "🎲 Dadu!",
            "🎉 Surprise!",
            "✨ Magic!",
            "😎 Ganteng banget gue!",
            String.valueOf(new Random().nextInt(100)),
            "🚀 To the moon!",
            "🍕 Waktunya pizza!",
            "🧩 Puzzle!",
            "🤖 Robot coop!"
    };

}


