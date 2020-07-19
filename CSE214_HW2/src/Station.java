import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The Class Station will contain a list of Track objects (which are themselves
 * lists of Train objects) that the user can switch between in the menu. It also
 * contains the main method that allows the user to interact with the Trains in
 * the Tracks using the commands specified below:
 * 
 * @author Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *         ID: 111230826
 */
public class Station {

	private Track head;
	private Track tail;
	private Track cursor;

	/**
	 * Instantiates a new station.
	 */
	public Station() {
		head = null;
		tail = null;
		cursor = null;
	}

	/**
	 * Adds newTrack to this Station. The new Track object must be inserted into
	 * the list such that the list is sorted by track numbers. If there is
	 * already a Track object in this Station with the same track number as
	 * newTrack, do not insert the new Track and throw an appropriate exception.
	 * Note that after inserting a Track, the currently selected Track is set to
	 * the newly inserted Track.
	 *
	 * @param newTrack 
	 * 	the new track
	 * @throws SameTrackNumberException 
	 * 	thrown if new track added has same track number as any other track.
	 */
	public void addTrack(Track newTrack) throws SameTrackNumberException {
		Track tempPtrList = head;
		while (tempPtrList != null) {
			if (tempPtrList.getTrackNumber() == newTrack.getTrackNumber()) {
				throw new SameTrackNumberException(
						"Track not added: Track "+ tempPtrList.getTrackNumber()
						+ " already exists!");
			} else {
				tempPtrList = tempPtrList.getNext();
			}
		}

		if (head == null) {
			head = newTrack;
			tail = newTrack;
			cursor = newTrack;
		}

		else if (newTrack.getTrackNumber() < head.getTrackNumber()) {
			newTrack.setNext(head);
			head.setPrev(newTrack);
			head = newTrack;
			cursor = newTrack;
		} else if (newTrack.getTrackNumber() > tail.getTrackNumber()) {
			newTrack.setPrev(tail);
			tail.setNext(newTrack);
			tail = newTrack;
			cursor = newTrack;
		} else {
			Track tmpPtr = head;
			while (tmpPtr != null) {
				if (newTrack.getTrackNumber() < tmpPtr.getTrackNumber()) {
					newTrack.setNext(tmpPtr);
					newTrack.setPrev(tmpPtr.getPrev());

					tmpPtr.getPrev().setNext(newTrack);
					tmpPtr.setPrev(newTrack);

					cursor = newTrack;
					return;
				} else {
					tmpPtr = tmpPtr.getNext();
				}
			}
			System.out.println("Track not added to station: REASON UNKNOWN");
		}
	}

	/**
	 * Removes the selected Track object from this Station and returns it. If
	 * there is no Track selected, returns null. The selected Track should now
	 * be the Track node directly after the one which was removed. If there is
	 * no Track node after the one which was removed, then selected Track should
	 * now be the node directly before the one which was removed. If the Track
	 * which was removed was the only one in Station list, then the selected
	 * Track should now be null.
	 *
	 * @return 
	 * 	the track if it exists or null if none were found
	 */
	public Track removeSelectedTrack() {
		if (cursor == null) {
			return null;
		} else if (head.getTrackNumber() == tail.getTrackNumber()) {
			Track tmpPtr = cursor;
			head = null;
			tail = null;
			cursor = null;
			return tmpPtr;
		} else if (head.getTrackNumber() == cursor.getTrackNumber()) {
			Track tmpPtr = cursor;
			cursor = cursor.getNext();
			cursor.setPrev(null);
			head = cursor;
			return tmpPtr;
		} else if (tail.getTrackNumber() == cursor.getTrackNumber()) {
			Track tmpPtr = cursor;
			cursor = cursor.getPrev();
			cursor.setNext(null);
			tail = cursor;
			return tmpPtr;
		} else {
			Track tmpPtr = cursor;
			Track tempPtr = cursor.getPrev();
			cursor = cursor.getNext();
			cursor.setPrev(tempPtr);
			tempPtr.setNext(cursor);
			return tmpPtr;
		}
	}

	/**
	 * Prints the selected Track list.
	 */
	public void printSelectedTrack() {
		System.out.println("Track " + cursor.getTrackNumber() + " ("
				+ String.format("%.2f", cursor.getUtilizationRate())
				+ "% Utilization Rate):");
		System.out.println(cursor.toString());
	}

	/**
	 * Prints all Track lists in this Station.
	 */
	public void printAllTracks() {
		Track tmpPtr = head;
		while (tmpPtr != null) {
			if (tmpPtr == cursor) {
				System.out.println("Track " + tmpPtr.getTrackNumber() + "* ("
						+ String.format("%.2f", tmpPtr.getUtilizationRate())
						+ "% Utilization Rate):");
				System.out.println(tmpPtr.toString());
				tmpPtr = tmpPtr.getNext();
				System.out.println();
			} else {
				System.out.println("Track " + tmpPtr.getTrackNumber() + " ("
						+ String.format("%.2f", tmpPtr.getUtilizationRate())
						+ "% Utilization Rate):");
				System.out.println(tmpPtr.toString());
				tmpPtr = tmpPtr.getNext();
				System.out.println();
			}
		}
	}

	/**
	 * Moves the reference of the selected Track node to the node which has the
	 * same trackNumber as the given trackToSelect parameter if it exists and
	 * returns true. If there is no Track with a trackNumber which matches
	 * trackToSelect, then the selected Track object should remain the same and
	 * return false.
	 *
	 * @param trackToSelect 
	 * 	the track to select
	 * 
	 * @return 
	 * 	null if cursor is null, true if a track exists from trackToSelect, 
	 * 	else false.
	 */
	public Boolean selectTrack(int trackToSelect) {
		if (cursor == null) {
			return false;
		} else {
			Track tmpPointer = head;
			while (tmpPointer != null) {
				if (tmpPointer.getTrackNumber() == trackToSelect) {
					cursor = tmpPointer;
					return true;
				}
				tmpPointer = tmpPointer.getNext();
				
			}
			return false;
		}
	}

	/**
	 * Returns a neatly formatted representation of this Station object.
	 *
	 * @return 
	 * 	the string of Station object
	 */
	public String toString() {
		String output = "";
		Track tmpPtr = head;
		int stationSize = 0;
		while (tmpPtr != null) {
			output = output + "Track " + tmpPtr.getTrackNumber() + ": "
					+ tmpPtr.size() + " trains arriving ("
					+ String.format("%.2f", tmpPtr.getUtilizationRate())
					+ "% Utilization Rate)\n";
			stationSize++;
			tmpPtr = tmpPtr.getNext();

		}
		output = "Station (" + stationSize + " tracks):\n" + output;

		return output;
	}

	/**
	 * The main method that allows the user to interact with Trains and Tracks
	 * at the Station.
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Station station = new Station();
		while (true) {
			System.out.println(
					"|---------------------------------------"
					+ "--------------------------------------|\n"
					+ "| Train Options                       "
					+ "| Track Options                         |\n"
					+ "|    A. Add new Train                 "
					+ "|    TA. Add Track                      |\n"
					+ "|    N. Select next Train             "
					+ "|    TR. Remove selected Track          |\n"
					+ "|    V. Select previous Train         "
					+ "|    TS. Switch Track                   |\n"
					+ "|    R. Remove selected Train         "
					+ "|   TPS. Print selected Track           |\n"
					+ "|    P. Print selected Train          "
					+ "|   TPA. Print all Tracks               |\n"
					+ "|-------------------------------------"
					+ "----------------------------------------|\n"
					+ "| Station Options                     "
					+ "                                        |\n"
					+ "|   SI. Print Station Information     "
					+ "                                        |\n"
					+ "|    Q. Quit                          "
					+ "                                        |\n"
					+ "|-------------------------------------"
					+ "----------------------------------------|\n");
			System.out.print("Choose an operation: ");
			String command = input.next().toUpperCase();
			System.out.println("");
			switch (command) {
			case ("A"):
				System.out.print("Enter Train Number: ");
				try {
					int trainNum = input.nextInt();
					System.out.print("Enter train destination: ");
					input.nextLine();
					String trainDescription = input.nextLine();
					System.out.print("Enter train arrival time: ");
					int arrivalTime = input.nextInt();
					System.out.print("Enter transfer time: ");
					int transferTime = input.nextInt();
					Train newTrain = new Train(trainNum, trainDescription,
							arrivalTime, transferTime);
					if (station.cursor == null) {
						System.out.println(
								"Train not added: There is no Track to add the "
								+ "Train to!\n\n");
					} else {
						station.cursor.addTrain(newTrain);
					}
				} catch (SameTrainNumberException ex) {
					System.out.println(ex.getMessage() + "\n\n");
				} catch (ConflictingScheduleException ex) {
					System.out.println(ex.getMessage() + "\n\n");
				} catch (InputMismatchException ex) {
					System.out.println("Input mismatch. Train not added.\n\n");
				}
				break;
			case ("N"):
				if (station.cursor == null) {
					System.out.println(
							"There is no Track! Add a track first.\n\n");
				} else {
					try {
						if (station.cursor.selectNextTrain()) {
							System.out.println(
									"Cursor has been moved to next train.\n\n");
						} else {
							System.out.println(
									"Selected train not updated: Already at end"
									+ " of Track list.\n\n");
						}
					} catch (NoTrainFoundException e) {
						System.out.println(e.getMessage() + "\n\n");
					}
				}
				break;
			case ("V"):
				if (station.cursor == null) {
					System.out.println(
							"There is no Track! Add a track first.\n\n");
				} else {
					try {
						if (station.cursor.selectPrevTrain()) {
							System.out.println(
									"Cursor has been moved to previous train."
									+ "\n\n");
						} else {
							System.out.println(
									"Selected train not updated: Already at "
									+ "beginning of Track list.\n\n");
						}
					} catch (NoTrainFoundException e) {
						System.out.println(e.getMessage() + "\n\n");
					}
				}
				break;
			case ("R"):
				if (station.cursor == null) {
					System.out.println(
							"There is no Track! Add a track first.\n\n");
				} else {
					Train selTrain = station.cursor.removeSelectedTrain();
					if (selTrain == null) {
						System.out.println(
								"This track is empty. There is no Train to "
								+ "remove.\n\n");
					} else {
						System.out.println("Train No. "
								+ selTrain.getTrainNumber() + " to "
								+ selTrain.getDestination()
								+ " has been removed from Track "
								+ station.cursor.getTrackNumber() + "\n\n");
					}
				}
				break;

			case ("P"):
				if (station.cursor == null) {
					System.out.println(
							"There is no Track! Add a track first.\n\n");
				} else {
					station.cursor.printSelectedTrain();
					System.out.println("\n\n");
				}

				break;
			case ("TA"):
				try {
					System.out.print("Enter track number: ");
					int trackNum = input.nextInt();
					Track newTrack = new Track(trackNum);
					station.addTrack(newTrack);
					System.out.println(
							"Track " + trackNum + " added to the station.\n\n");
				} catch (SameTrackNumberException e) {
					System.out.println(e.getMessage() + "\n\n");
				} catch (InputMismatchException e) {
					System.out.println(
							"Input mismatch. Track was not added.\n\n");
				}

				break;
			case ("TR"):
				if (station.cursor == null) {
					System.out.println(
							"There is no Track to remove! Add a track first."
							+ "\n\n");
				} else {
					System.out.println("Closed Track "
							+ station.removeSelectedTrack().getTrackNumber()
							+ ".\n\n");
				}
				break;
			case ("TS"):
				try {
					System.out.print("Enter the Track Number: ");
					int trackNum = input.nextInt();
					if (station.cursor == null) {
						System.out.println(
								"There is no Track! Add a track first.\n\n");
					} else if (station.selectTrack(trackNum)) {
						System.out.println(
								"Switched to Track " + trackNum + ".\n\n");
					} else {
						System.out.println("Could not switch to Track "
								+ trackNum + ": Track " + trackNum
								+ " does not exist.\n\n");
					}
				} catch (InputMismatchException e) {
					System.out.println("Track not found. Input mismatch.\n\n");
				}

				break;
			case ("TPS"):
				if (station.cursor == null) {
					System.out.println(
							"There is no Track! Add a track first.\n\n");
				} else {
					station.printSelectedTrack();
					System.out.println();
				}
				break;
			case ("TPA"):
				if (station.cursor == null) {
					System.out.println(
							"There is no Track! Add a track first.\n\n");
				} else {
					station.printAllTracks();
				}
				break;
			case ("SI"):
				if (station.cursor == null) {
					System.out.println(
							"There is no Track! Add a track first.\n\n");
				} else {
					System.out.println(station.toString());
				}
				break;
			case ("Q"):
				System.out.println("Program terminating normally...");
				input.close();
				return;
			default:
				System.out.print("The command does not exist. Try again. \n\n");
			}
		}
	}
}
