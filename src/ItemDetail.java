public class ItemDetail {
    private Item item;
    private double minSup;
    private int count;

    public ItemDetail(Item item, double minSup) {
        this.item = item;
        this.minSup = minSup;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public double getMinSup() {
        return minSup;
    }

    public void setMinSup(double minSup) {
        this.minSup = minSup;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
