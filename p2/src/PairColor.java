public class PairColor {

    DoublyLinkedList count;
    WaterColor color;

    public PairColor(WaterColor color, DoublyLinkedList count){
        this.color = color;
        this.count = count;
    }

    public DoublyLinkedList getCount(){
        return count;
    }

    public WaterColor getColor() {
        return color;
    }
}
