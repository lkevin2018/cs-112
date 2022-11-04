package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class contains methods which, when used together, perform the
 * entire Huffman Coding encoding and decoding process
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 * @author Kevin Joseph
 *         kbj24
 *         kbj24@rutgers.edu
 */
public class HuffmanCoding {
    private String fileName;
    private ArrayList<CharFreq> sortedCharFreqList;
    private TreeNode huffmanRoot;
    private String[] encodings;

    /**
     * Constructor used by the driver, sets filename
     * DO NOT EDIT
     * @param f The file we want to encode
     */
    public HuffmanCoding(String f) { 
        fileName = f; 
    }

    private ArrayList<CharFreq> sortByFreq(ArrayList<CharFreq> unsorted){
        Collections.sort(unsorted);
        return unsorted;
    }
    /**
     * Reads from filename character by character, and sets sortedCharFreqList
     * to a new ArrayList of CharFreq objects with frequency > 0, sorted by frequency
     */
    public void makeSortedList() {
        StdIn.setFile(fileName);
        ArrayList<CharFreq> lst = new ArrayList<>();
        ArrayList<Character> chars = new ArrayList<>();
        ArrayList<Integer> count = new ArrayList<>();

        int charCount = 0;
        chars.add(StdIn.readChar());
        count.add(1);
        charCount++;
        while(StdIn.hasNextChar()){
            Character temp = StdIn.readChar();
            charCount++;
            for(int i = 0; i < chars.size(); i++){
                if(chars.get(i).equals(temp)){
                    count.set(i, count.get(i) + 1);
                    break;
                }
                else if(i == chars.size() - 1){
                    chars.add(temp);
                    count.add(1);
                    break;
                }
            }
        }
        if(chars.size() == 1){
            if(chars.get(0) == 127){
                chars.add((char) 0);
                count.add(0);
            }
            else{
                chars.add((char)(chars.get(0)+1));
                count.add(0);
            }
        }
        for(int i = 0; i < chars.size(); i++){
            double temp = count.get(i);
            double prob = (temp/charCount);
            lst.add(new CharFreq(chars.get(i), prob));
        }
        sortedCharFreqList = sortByFreq(lst);
    }

    /**
     * Uses sortedCharFreqList to build a huffman coding tree, and stores its root
     * in huffmanRoot
     */
    public void makeTree() {
        Queue<TreeNode> source = new Queue<TreeNode>();
        Queue<TreeNode> target = new Queue<TreeNode>();
        for(int i = 0; i < sortedCharFreqList.size(); i++){
            source.enqueue(new TreeNode(sortedCharFreqList.get(i), null, null));
        }
        while(source.size() + target.size() > 1){
            TreeNode left = new TreeNode();
            TreeNode right = new TreeNode();
            for(int i = 0; i < 2; i++){
                if(source.isEmpty()){
                    TreeNode temp = target.dequeue();
                    if(i == 0){
                        left = temp;
                    }
                    else{
                        right = temp;
                    }
                }
                else if(target.isEmpty()){
                    TreeNode temp = source.dequeue();
                    if(i == 0){
                        left = temp;
                    }
                    else{
                        right = temp;
                    }
                }
                else if(source.peek().getData().getProbOcc() <= target.peek().getData().getProbOcc()){
                    TreeNode temp = source.dequeue();
                    if(i == 0){
                        left = temp;
                    }
                    else{
                        right = temp;
                    }
                }
                else{
                    TreeNode temp = target.dequeue();
                    if(i == 0){
                        left = temp;
                    }
                    else{
                        right = temp;
                    }
                }
            }
            CharFreq nullC = new CharFreq(null, left.getData().getProbOcc() + right.getData().getProbOcc());
            TreeNode sum = new TreeNode(nullC, left, right);
            target.enqueue(sum);
        }
        huffmanRoot = target.dequeue();
}
    private void populateEncodings(TreeNode root, Character c, String res){
        if(root == null){
            res = res.substring(0, res.length()-2);
            return;
        }
        populateEncodings(root.getLeft(), c, res + "0");
        if(root.getData().getCharacter() == c){
            encodings[c] = res;
            return;
        }
        populateEncodings(root.getRight(), c, res + "1");
    }
    /**
     * Uses huffmanRoot to create a string array of size 128, where each
     * index in the array contains that ASCII character's bitstring encoding. Characters not
     * present in the huffman coding tree should have their spots in the array left null.
     * Set encodings to this array.
     */
    public void makeEncodings() {
        encodings = new String[128];
        int count = sortedCharFreqList.size();
        for(int i = 0; i < count; i++){
            Character temp = sortedCharFreqList.get(i).getCharacter();
            populateEncodings(huffmanRoot, temp, "");
        }
    }

    /**
     * Using encodings and filename, this method makes use of the writeBitString method
     * to write the final encoding of 1's and 0's to the encoded file.
     * 
     * @param encodedFile The file name into which the text file is to be encoded
     */
    public void encode(String encodedFile) {
        StdIn.setFile(fileName);
        String result = "";
        while(StdIn.hasNextChar()){
            Character temp = StdIn.readChar();
            result += encodings[temp];
        }
        writeBitString(encodedFile, result);
    }
    
    /**
     * Writes a given string of 1's and 0's to the given file byte by byte
     * and NOT as characters of 1 and 0 which take up 8 bits each
     * DO NOT EDIT
     * 
     * @param filename The file to write to (doesn't need to exist yet)
     * @param bitString The string of 1's and 0's to write to the file in bits
     */
    public static void writeBitString(String filename, String bitString) {
        byte[] bytes = new byte[bitString.length() / 8 + 1];
        int bytesIndex = 0, byteIndex = 0, currentByte = 0;

        // Pad the string with initial zeroes and then a one in order to bring
        // its length to a multiple of 8. When reading, the 1 signifies the
        // end of padding.
        int padding = 8 - (bitString.length() % 8);
        String pad = "";
        for (int i = 0; i < padding-1; i++) pad = pad + "0";
        pad = pad + "1";
        bitString = pad + bitString;

        // For every bit, add it to the right spot in the corresponding byte,
        // and store bytes in the array when finished
        for (char c : bitString.toCharArray()) {
            if (c != '1' && c != '0') {
                System.out.println("Invalid characters in bitstring");
                return;
            }

            if (c == '1') currentByte += 1 << (7-byteIndex);
            byteIndex++;
            
            if (byteIndex == 8) {
                bytes[bytesIndex] = (byte) currentByte;
                bytesIndex++;
                currentByte = 0;
                byteIndex = 0;
            }
        }
        
        // Write the array of bytes to the provided file
        try {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(bytes);
            out.close();
        }
        catch(Exception e) {
            System.err.println("Error when writing to file!");
        }
    }

    /**
     * Using a given encoded file name, this method makes use of the readBitString method 
     * to convert the file into a bit string, then decodes the bit string using the 
     * tree, and writes it to a decoded file. 
     * 
     * @param encodedFile The file which has already been encoded by encode()
     * @param decodedFile The name of the new file we want to decode into
     */
    public void decode(String encodedFile, String decodedFile) {
        StdOut.setFile(decodedFile);
        String input = readBitString(encodedFile);
        String output = "";
        while(input.length() > 0){
            boolean found = false;
            String temp = "";
            TreeNode iteR = huffmanRoot;
            while(!found){
                char c1 = input.charAt(0);
                if(c1 == '0'){
                    temp += '0';
                    input = input.substring(1);
                    if(iteR.getLeft().getData().getCharacter() != null){
                        found = true;
                        output += iteR.getLeft().getData().getCharacter(); 
                    }
                    else{
                        iteR = iteR.getLeft();
                    }
                }
                else{
                    temp += '1';
                    input = input.substring(1);
                    if(iteR.getRight().getData().getCharacter() != null){
                        found = true;
                        output += iteR.getRight().getData().getCharacter(); 
                    }
                    else{
                        iteR = iteR.getRight();
                    }
                }
            }
        }
        StdOut.print(output);
    }

    /**
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * DO NOT EDIT
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     */
    public static String readBitString(String filename) {
        String bitString = "";
        
        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();
            
            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bitString = bitString + 
                String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bitString.charAt(i) == '1') return bitString.substring(i+1);
            }
            
            return bitString.substring(8);
        }
        catch(Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    /*
     * Getters used by the driver. 
     * DO NOT EDIT or REMOVE
     */

    public String getFileName() { 
        return fileName; 
    }

    public ArrayList<CharFreq> getSortedCharFreqList() { 
        return sortedCharFreqList; 
    }

    public TreeNode getHuffmanRoot() { 
        return huffmanRoot; 
    }

    public String[] getEncodings() { 
        return encodings; 
    }
}
