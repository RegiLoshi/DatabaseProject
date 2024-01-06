public class Book {
    private long ISBN;
    private String title;
    private String author;
    private int quantity;

    public Book(long ISBN, String title, String author, int quantity) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

	public long getISBN() {
		return ISBN;
	}

	public void setISBN(long iSBN) {
		ISBN = iSBN;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
