public class ItemDetail {
    Item item;
    double minSup;
    int count;

    public ItemDetail(Item item, double minSup, int count) {
        this.item = item;
        this.minSup = minSup;
        this.count = count;
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
