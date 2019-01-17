package com.example.yusuf.khysapp.LinkedList;
public class MyLinkedList
{
    private int lastindex;
    private Node header;
    ////////////////////********************------CONSTRUCTORS--------********////////////////////////////////////////////////////
    public MyLinkedList()
    {
        header = new Node();
    }

    ////////////////////********************------FUNCTIONS--------********////////////////////////////////////////////////////
    public void AddStrArray(String[] array)
    {
        Node currentnode = header;
        int lenght = array.length;

        for(int i = 0;i<lenght;i++)
        {
            currentnode.index = i;
            if(array[i].startsWith("SentFromMe:"))
            {
                array[i] = array[i].substring(11);
                currentnode.fromMe=true;
            }
            else if(array[i].startsWith("SentFromOther:"))
            {
                array[i] = array[i].substring(14);
                currentnode.fromMe=false;
            }
            currentnode.item=array[i];
            currentnode.next = new Node();
            currentnode=currentnode.next;
            lastindex++;
        }
    }
    public boolean isFromMe(int index)
    {
        Node currentnode=header;
        while(currentnode.index != index)
        {
            currentnode= currentnode.next;
        }
        if(currentnode.fromMe == true)
            return true;
        else
            return false;
    }
    public int getLastindex()
    {
        return lastindex;
    }
    public String getString(int index)
    {
        Node currentnode=header;
        while(currentnode.index != index)
        {
            currentnode=currentnode.next;
        }
        return currentnode.item;
    }
    public void DeleteAll()
    {
        header = new Node();
    }

}
class Node
{
    int index = 0;
    String item;
    boolean fromMe;
    Node next;
}