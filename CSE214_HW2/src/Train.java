
/**
 * The Class Train that contains basic information for a train approaching a
 * station. Required information includes the train number, the train's
 * destination, the arrival time of the train to its track (in 24-hour format
 * between 0000 - 2359), and the transfer time for how long the train waits at
 * the station (in minutes).
 *
 * @author Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *         ID: 111230826
 */
public class Train {
	private Train next;
	private Train prev;
	private int trainNumber;
	private String destination;
	private int arrivalTime;
	private int transferTime;

	/**
	 * Instantiates a new train.
	 */
	public Train() {
		this.next = null;
		this.prev = null;
		this.trainNumber = 0;
		this.destination = "";
		this.arrivalTime = 0;
		this.transferTime = 0;
	}

	/**
	 * Instantiates a new train.
	 * 
	 * @param trainNumber  
	 * 	number of the train
	 * 
	 * @param destination  
	 * 	destination of train
	 * 
	 * @param arrivalTime  
	 * 	time of arrival
	 * 
	 * @param transferTime 
	 * 	time of transfer
	 */
	public Train(int trainNumber, String destination, int arrivalTime,
			int transferTime) {
		this.next = null;
		this.prev = null;
		this.trainNumber = trainNumber;
		this.destination = destination;
		this.arrivalTime = arrivalTime;
		this.transferTime = transferTime; 
	}

	/**
	 * Retrieves train number.
	 * 
	 * @return 
	 * 	train number
	 */
	public int getTrainNumber() {
		return trainNumber;
	}

	/**
	 * Retrieves destination of train.
	 * 
	 * @return 
	 * 	destination of train
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * Retrieves arrival time.
	 * 
	 * @return 
	 * 	arrival time
	 */
	public int getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * Retrieves transfer time.
	 * 
	 * @return 
	 * 	transfer time
	 */
	public int getTransferTime() {
		return transferTime;
	}

	/**
	 * Sets the next Train in the list.
	 *
	 * @param nextTrain 
	 * 	the new next
	 */
	public void setNext(Train nextTrain) {
		this.next = nextTrain;
	}

	/**
	 * Sets the previous Train in the list.
	 *
	 * @param prevTrain 
	 * 	the new prev
	 */
	public void setPrev(Train prevTrain) {
		this.prev = prevTrain;
	}

	/**
	 * Returns the next Train in the list. If there is no next Train, returns
	 * null.
	 *
	 * @return 
	 * 	the next Train or null
	 */
	public Train getNext() {
		if (next != null) {
			return next;
		} else {
			return null;
		}
	}

	/**
	 * Returns the next Train in the list. If there is no next Train, returns
	 * null.
	 *
	 * @return 
	 * 	the previous Train or null
	 */
	public Train getPrev() {
		if (prev != null) {
			return prev;
		} else {
			return null;
		}
	}

	/**
	 * Check if this Train object is equal to the supplied parameter. Two Train
	 * objects are considered equal if and only if their Train numbers are
	 * equal.
	 *
	 * @param train 
	 * 	the train object
	 * 
	 * @return 
	 * 	true if train number is equal, else false
	 */
	public boolean equals(Object train) {
		if (train instanceof Train
				&& ((Train) train).getTrainNumber() == this.trainNumber) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns a textual representation of all the information for this Train
	 * object.
	 *
	 * @return 
	 * 	the string
	 */
	public String toString() {
		return "Selected Train:\n" + "\tTrain Number: " + this.trainNumber
				+ "\n" + "\tTrain Destination: " + this.destination + "\n"
				+ "\tArrival Time: " + String.format("%04d", this.arrivalTime)
				+ "\n" + "\tDeparture Time: ";
	}

}
