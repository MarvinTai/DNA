public class LinkStrand implements IDnaStrand {

    private class Node {
        String info;
        Node next;

        public Node(String s) {
            info = s;
            next = null;
        }

        public Node(String s, Node n) {
            info = s;
            next = n;
        }
    }

    private Node myFirst, myLast;
    private long mySize;
    private int myAppends;
    private int myIndex;
    private Node myCurrent;
    private int myLocalIndex;

    public LinkStrand() {
        this("");
    }

    public LinkStrand(String s) {
        initialize(s);
    }

    @Override
    public void initialize(String source) {
        myFirst = new Node(source);
        myLast = myFirst;
        mySize = source.length();
        myAppends = 0;
        myIndex = 0;
        myCurrent = myFirst;
        myLocalIndex = 0;
    }

    @Override
    public long size() {
        return mySize;
    }

    // get instance --> return the new LinkStrand

    @Override
    public IDnaStrand getInstance(String source) {
        return new LinkStrand(source);
    }

    private void addFirst(String s) {
        Node newfirst = new Node(s, myFirst);
        myFirst = newfirst;
        mySize += s.length();
    }

    @Override
    public IDnaStrand append(String dna) {
        myLast.next = new Node(dna);
        myLast = myLast.next;
        mySize += dna.length();
        myAppends += 1;
        return this;
    }

    @Override
    public int getAppendCount() {
        return myAppends;
    }

    // object.toString()

    @Override
    public String toString() {
        Node temp = myFirst;
        StringBuilder sb = new StringBuilder();
        while (temp != null) {
            sb.append(temp.info);
            temp = temp.next;
        }
        return sb.toString();
    }

    // object.append("smth")
    // append("something")

    @Override
    public IDnaStrand reverse() {
        LinkStrand rev = new LinkStrand();
        Node current = myFirst;
        while (current != null) {
            StringBuilder temp = new StringBuilder(current.info);
            rev.addFirst(temp.reverse().toString());
            current = current.next;
        }
        return rev;
    }

    @Override
    public char charAt(int index) {
        if (index > mySize-1|| index< 0)
        {
            throw new IndexOutOfBoundsException(String.format("Tried to access char %s while strand has size %s",index, mySize));
        }

        if (myIndex== 0|| index < (myIndex - myLocalIndex))
        {
            return iterativeCharAt(index, myFirst, 0);
        }
        return iterativeCharAt(index, myCurrent, myIndex-myLocalIndex);
    }
    
    private char iterativeCharAt(int index, Node nodestart, int precedingNodeCount)
    {
        int totalcount = precedingNodeCount;
        int localcount = 0;
        Node list = nodestart;
        
        while(totalcount != index)
        {
            // if the index is not on the current node then we skip it 
            if (index>totalcount+list.info.length()-1)
            {
                totalcount += list.info.length();
                list = list.next;
            }
            else 
            {
                localcount = index-totalcount;
                totalcount = index;
            }
        }
        myIndex = index;
        myLocalIndex = localcount;
        myCurrent = list;

        return list.info.charAt(localcount);
    }

}
