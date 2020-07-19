
/**
 * The Class Track that contains references to the head and tail of a list of
 * Train nodes, as well as a cursor representing the selected Train node. You
 * also want to keep in mind the Track utilization rate for what percentage of
 * the day the track is being used (total time of trains waiting at the track /
 * total minutes in a day). Each track also contains a track number, which must
 * be unique among all Track objects (there can only be one 'Track 1'). The
 * Train objects stored in your Track must be sorted according to the arrival
 * times of the trains. Your class will follow this specification, but you have
 * to fill in the details:
 * 
 * @author Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *         ID: 111230826
 */
public class Track {

	private Train head;
	private Train tail;
	private Train cursor;
	private Track next;
	private Track prev;
	private double utilizationRate;
	private int trackNumber;
	private int trackSize;

	/**
	 * Instantiates a new track.
	 */
	public Track() {
		head = null;
		tail = null;
		cursor = null;
		next = null;
		prev = null;
		trackNumber = 0;
		utilizationRate = 0.0;
		trackSize = 0;
	}

	/**
	 * Instantiates a new track with given track number.
	 * 
	 * @param trackNum 
	 * 	the number of the given track
	 */
	public Track(int trackNum) {
		head = null;
		tail = null;
		cursor = null;
		next = null;
		prev = null;
		trackNumber = trackNum;
		utilizationRate = 0.0;
		trackSize = 0;
	}

	/**
	 * Get next track in linkedlist
	 * 
	 * @return 
	 * 	the next track
	 */
	public Track getNext() {
		return next;
	}

	/**
	 * Set the next track.
	 * 
	 * @param newTrack 
	 * 	the next track
	 */
	public void setNext(Track newTrack) {
		next = newTrack;
	}

	/**
	 * Get prev track in linkedlist
	 * 
	 * @return 
	 * 	the prev track
	 */
	public Track getPrev() {
		return prev;
	}

	/**
	 * Set prev track in linkedlist
	 * 
	 * @param newTrack 
	 * 	the prev track
	 */
	public void setPrev(Track newTrack) {
		prev = newTrack;
	}

	/**
	 * Retrieves utilization rate from track
	 * 
	 * @return 
	 * 	utilization value
	 */
	public double getUtilizationRate() {
		return utilizationRate;
	}

	/**
	 * Sets the track number.
	 * 
	 * @param trackNumber 
	 * 	the number of the track
	 */
	public void setTrackNumber(int trackNumber) {
		this.trackNumber = trackNumber;
	}

	/**
	 * Returns the track number.
	 * 
	 * @return 
	 * 	The track number
	 */
	public int getTrackNumber() {
		return trackNumber;
	}

	/**
	 * Adds a new Train to the specified Track list. After insertion, the
	 * currently selected train for this Track object should be updated to be
	 * the newly inserted Train.
	 *
	 * @param newTrain 
	 * 	the new train
	 * 
	 * @throws SameTrainNumberException     
	 * 	Determines if there a train with the same track number.
	 * 
	 * @throws ConflictingScheduleException 
	 * 	Thrown if there a train with conflicting schedules.
	 */
	public void addTrain(Train newTrain)
			throws SameTrainNumberException, ConflictingScheduleException {
		int newTrainTime = newTrain.getArrivalTime();
		int ntTransferTime = newTrain.getTransferTime();
		int minsInDay = 1440;
		Train tempPtrList = head;
		while (tempPtrList != null) {
			if (tempPtrList.equals(newTrain)) {
				throw new SameTrainNumberException(
						"Train not added: There is already a Train with that "
						+ "number!");
			} else if ((newTrainTime >= tempPtrList.getArrivalTime()
					&& newTrainTime <= departureTimeFormat(tempPtrList))
					|| (departureTimeFormat(newTrain) >= tempPtrList
							.getArrivalTime()
							&& departureTimeFormat(
									newTrain) <= departureTimeFormat(
											tempPtrList))) {
				throw new ConflictingScheduleException(
						"Train not added: There is a Train already scheduled "
						+ "on Track " + getTrackNumber() + " at that time!");
			} else {
				tempPtrList = tempPtrList.getNext();
			}
		}

		if (!isValidTimeFormat(newTrain)) {
			System.out.println("Train not added: Invalid arrival");
		} else if (departureTimeFormat(newTrain)==-1) {
			System.out.println("Train not added: Train overlaps to next day.");
		} else if (head == null) {
			head = newTrain;
			tail = newTrain;
			cursor = tail;
			double val = ((double) ntTransferTime / (double) minsInDay) * 100.0;
			utilizationRate = utilizationRate + val;
			trackSize++;

			System.out.println("Train No. " + newTrain.getTrainNumber() + " to "
					+ newTrain.getDestination() + " added to Track "
					+ trackNumber);
		} else if (departureTimeFormat(newTrain) < head.getArrivalTime()) {
			newTrain.setNext(head);
			head.setPrev(newTrain);
			head = newTrain;
			cursor = newTrain;
			trackSize++;
			double val = ((double) ntTransferTime / (double) minsInDay) * 100.0;
			utilizationRate = utilizationRate + val;
			System.out.println("Train No. " + newTrain.getTrainNumber() + " to "
					+ newTrain.getDestination() + " added to Track "
					+ trackNumber);
		} else if (newTrainTime > departureTimeFormat(tail)) {
			newTrain.setPrev(tail);
			tail.setNext(newTrain);
			tail = newTrain;
			cursor = newTrain;
			trackSize++;
			double val = ((double) ntTransferTime / (double) minsInDay) * 100.0;
			utilizationRate = utilizationRate + val;
			System.out.println("Train No. " + newTrain.getTrainNumber() + " to "
					+ newTrain.getDestination() + " added to Track "
					+ trackNumber);
		} else {
			Train tempPtr = head.getNext();
			while (tempPtr != null) {
				int ntDepTime = departureTimeFormat(newTrain);
				int ptrPrevDepTime = departureTimeFormat(tempPtr.getPrev());
				if (ntDepTime < tempPtr.getArrivalTime()
						&& ntDepTime > ptrPrevDepTime 
						&& newTrain.getArrivalTime() > ptrPrevDepTime) {
					
					newTrain.setNext(tempPtr);
					newTrain.setPrev(tempPtr.getPrev());
					tempPtr.getPrev().setNext(newTrain);
					tempPtr.setPrev(newTrain);

					cursor = newTrain;
					trackSize++;
					double val = ((double) ntTransferTime / (double) minsInDay)
							* 100.0;
					utilizationRate = utilizationRate + val;
					System.out.println("Train No. " + newTrain.getTrainNumber()
							+ " to " + newTrain.getDestination()
							+ " added to Track " + trackNumber);
					return;
				}
				tempPtr = tempPtr.getNext();
			}
			throw new ConflictingScheduleException(
					"Train not added: There is a Train already scheduled "
					+ "on Track " + getTrackNumber() + " during that time!");
		}
	}

	/**
	 * Checks if arrival time is in the correct format for a Train object, as it
	 * must be set to military time. It also checks destination time if time 
	 * rolls over to next day.
	 * 
	 * @param newTrain 
	 * 	Train object
	 * 
	 * @return 
	 * 	True if format is correct military time, null if departure time format 
	 * 	rolls over to next day (back to 0000),else false
	 */
	public boolean isValidTimeFormat(Train newTrain) {
		int arrivalTime = newTrain.getArrivalTime();
		int arrivalIntLength = String.valueOf(arrivalTime).length();
		int transferTime = newTrain.getTransferTime();
		if (transferTime < 0) {
			return false;
		}
		if (arrivalTime < 60 && arrivalTime >= 0) {
			return true;
		} else if (arrivalIntLength > 2 && arrivalTime >= 0) {
			int mins = arrivalTime % 100;
			int hours = arrivalTime / 100;
			if (mins < 60 && hours < 24) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Creates and returns departure time of a Train object using its arrival
	 * time and transfer time. If departure time is greater than than max time
	 * (2359 Military time), return -1 to indicate an issue with the time.
	 * 
	 * @param newTrain 
	 * 	Train object
	 * 
	 * @return 
	 * 	departure time format
	 */
	public int departureTimeFormat(Train newTrain) {
		int arrivalTime = newTrain.getArrivalTime();
		int transferTime = newTrain.getTransferTime();
		int departureTime = arrivalTime + transferTime;
		int arrivalIntLength = String.valueOf(arrivalTime).length();
		if (departureTime < 60 && departureTime >= 0) {
			return departureTime;
		} else if (arrivalIntLength > 2) {
			int mins = arrivalTime % 100;
			int hours = arrivalTime / 100;
			int newMins = mins + transferTime;
			if (newMins >= 60) {
				hours = hours + newMins / 60;
				if(hours >= 24) {
					return -1;
				}
				mins = newMins % 60;
				return (hours * 100 + mins);
			} else {
				return departureTime;
			}
		} else {
			int mins = arrivalTime % 100;
			int hours = arrivalTime / 100;
			int newMins = mins + transferTime;
			hours = hours + newMins / 60;
			mins = newMins % 60;
			return (hours * 100 + mins);
		}
	}

	/**
	 * Prints the data of the selected train.
	 */
	public void printSelectedTrain() {
		if (cursor != null) {
			System.out.print(cursor.toString()
					+ String.format("%04d", departureTimeFormat(cursor)));
		} else {
			System.out.println(
					"Unable to print train information as there is no train on "
					+ "this track.");
		}
	}

	/**
	 * Removes the selected Train from the track and returns a reference to it.
	 * If there is no Train selected, return null. The selected Train should now
	 * be the Train node after the one which was just removed. If there is no
	 * Train after the one which was just removed, the selected Train should now
	 * be the node before the one which was just removed. If the Train removed
	 * was the only one in the Track list, then the selected Train should now be
	 * null.
	 *
	 * @return 
	 * 	the train thats removed
	 */
	public Train removeSelectedTrain() {
		int minsInDay = 1440;
		if (cursor == null) {
			return null;
		} else if (head.equals(tail)) {
			Train tmpPtr = cursor;
			//FIX UTILIZATION RATE WHEN REMOVING
			utilizationRate -= 
					((double) tmpPtr.getTransferTime() / (double)minsInDay)
					* 100.0;
			
			cursor = null;
			head = null;
			tail = null;
			trackSize--;
			return tmpPtr;
		} else if (cursor.equals(head)) {
			Train tmpPtr = cursor;
			utilizationRate -= 
					((double) tmpPtr.getTransferTime() / (double)minsInDay)
					* 100.0;
			cursor = cursor.getNext();
			cursor.setPrev(null);
			head = cursor;
			trackSize--;
			return tmpPtr;

		} else if (cursor.equals(tail)) {
			Train tmpPtr = cursor;
			utilizationRate -= 
					((double) tmpPtr.getTransferTime() / (double)minsInDay)
					* 100.0;
			cursor = cursor.getPrev();
			cursor.setNext(null);
			tail = cursor;
			trackSize--;
			return tmpPtr;
		} else {
			cursor.getPrev().setNext(cursor.getNext());
			Train tmpPtr = cursor;
			utilizationRate -= 
					((double) tmpPtr.getTransferTime() / (double)minsInDay)
					* 100.0;
			Train tempPtr = cursor.getPrev();
			cursor = cursor.getNext();
			cursor.setPrev(tempPtr);
			tempPtr.setNext(cursor);
			trackSize--;
			return tmpPtr;
		}
	}

	/**
	 * Moves the reference of the selected Train node forwards to the next Train
	 * if it exists and returns true. If there is no next Train, then the
	 * selected Train should remain the same and return false. If there is no
	 * selected Train, you should throw an appropriate exception.
	 *
	 * @throws NoTrainFoundException 
	 * 	thrown if the cursor is null, as there is no train linked.
	 *
	 * @return 
	 * 	true if there is a next train, false if there is not, else return null 
	 * 	if cursor is null.
	 */
	public Boolean selectNextTrain() throws NoTrainFoundException {
		if (cursor == null) {
			throw new NoTrainFoundException("No Train was found.");
		} else if (cursor.getNext() != null) {
			cursor = cursor.getNext();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Moves the reference of the selected Train node backwards to the previous
	 * Train if it exists and returns true. If there is no previous Train, then
	 * the selected Train should remain the same and return false. If there is
	 * no selected Train, you should throw an appropriate exception.
	 *
	 * @throws NoTrainFoundException 
	 * 	thrown if the cursor is null, as there is no train linked.
	 *
	 * @return 
	 * 	true if there is a previous train, false if there is not, else return 
	 * 	null if cursor is null.
	 */
	public Boolean selectPrevTrain() throws NoTrainFoundException {
		if (cursor == null) {
			throw new NoTrainFoundException("No Train was found.");
		} else if (cursor.getPrev() != null) {
			cursor = cursor.getPrev();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Retrieves the amount of train objects in the track.
	 * 
	 * @return 
	 * 	the size of the track
	 */
	public int size() {
		return trackSize;
	}

	/**
	 * Returns a neatly formatted list of all the trains scheduled on this
	 * Track.
	 *
	 * @return 
	 * 	the string for formatted list of trains
	 */
	public String toString() {
		Train tempPtr = head;
		System.out.println(
				"Selected  Train Number     Train Destination         Arrival "
				+ "Time     Departure Time");
		System.out.println(
				"-------------------------------------------------------------"
				+ "------------------------");
		String outputString = "";
		while (tempPtr != null) {
			String deptTime = String.format("%04d",
					departureTimeFormat(tempPtr));
			String arrTime = String.format("%04d", tempPtr.getArrivalTime());
			if (tempPtr.equals(cursor)) {
				outputString += String.format("%6s%12d\t    %-20s%12s%20s", "*",
						tempPtr.getTrainNumber(), tempPtr.getDestination(),
						arrTime, deptTime) + "\n";
				tempPtr = tempPtr.getNext();
			} else {
				outputString += String.format("%18d\t    %-20s%12s%20s",
						tempPtr.getTrainNumber(), tempPtr.getDestination(),
						arrTime, deptTime) + "\n";
				tempPtr = tempPtr.getNext();
			}
		}
		return outputString;
	}

}
