package liberryan;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import liberryan.validation.BookValidator;
import liberryan.validation.ErrorNotificationSynchronizer;
import liberryan.validation.ValidationErrorList;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

// The controller for the GUI.
public class Controller implements Initializable {
    private final ErrorNotificationSynchronizer<Book.Field> createBookErrorSynchronizer = new ErrorNotificationSynchronizer<>();
    private final ErrorNotificationSynchronizer<Book.Field> editBookErrorSynchronizer = new ErrorNotificationSynchronizer<>();
    private BookDatabase database = new BookDatabase();
    private ActivityTracker activityTracker = new ActivityTracker(database);
    private StatisticsEngine statisticsEngine = new StatisticsEngine(database);

    public TabPane tabPane;

    public Tab addBookTab;
    public Tab viewBookTab;
    public Tab recentActivityTab;
    public Tab statisticsTab;
    public Tab saveRestoreDataTab;

    // shortcut buttons
    public Button addNewBookShortcutBtn;
    public Button viewBooksShortcutBtn;
    public Button viewRecentActivityShortcutBtn;
    public Button viewStatisticsShortcutBtn;
    public Button saveRestoreDataShortcutBtn;

    // create book
    public TextField createBookNameTextField;
    public TextField createBookPageCountTextField;
    public ComboBox<Genre> createBookGenreComboBox;
    public TextField createBookAuthorNameTextField;
    public DatePicker createBookPublishedAtDatePicker;
    public ComboBox<BookFolder> createBookFolderComboBox;

    public Button addBookBtn;

    public Text createBookNameValidationFailureText;
    public Text createBookPageCountValidationFailureText;
    public Text createBookGenreValidationFailureText;
    public Text createBookAuthorNameValidationFailureText;
    public Text createBookPublishedAtValidationFailureText;
    public Text createBookFolderValidationFailureText;

    public TabPane bookFolderTab;
    public Tab wantToReadTab;
    public Tab currentlyReadingTab;
    public Tab alreadyReadTab;

    // edit book / book information
    public Text pagesReadInPastWeekText;

    public TextField editBookNameTextField;
    public TextField editBookPageCountTextField;
    public TextField editBookPagesReadSoFarTextField;
    public ComboBox<Genre> editBookGenreComboBox;
    public TextField editBookAuthorNameTextField;
    public DatePicker editBookPublishedAtDatePicker;

    // NOTE: Rating is something users can only see when editing, not creating, because it's rather uncommon
    // that one would rate a book when creating it.
    public TextField editBookRatingTextField;
    public ComboBox<BookFolder> editBookFolderComboBox;

    public Text editBookNameValidationFailureText;
    public Text editBookPageCountValidationFailureText;
    public Text editBookPagesReadSoFarValidationFailureText;
    public Text editBookGenreValidationFailureText;
    public Text editBookAuthorNameValidationFailureText;
    public Text editBookPublishedAtValidationFailureText;
    public Text editBookRatingValidationFailureText;
    public Text editBookFolderValidationFailureText;

    public Button saveEditsBtn;
    public Button deleteBookBtn;

    // view friends
    public ListView<Book> wantToReadListView;
    public ListView<Book> currentlyReadingListView;
    public ListView<Book> alreadyReadListView;

    public AnchorPane bookInfoAnchorPane;

    // recent activity
    public Text weekdayWithMostActivityText;
    public Text weekdayWithLeastActivityText;

    public ListView<ActivityTracker.ReadingActivityEntry> readingActivityByWeekdayListView;
    public ListView<ActivityTracker.ActivityLogEntry> activityLogListView;

    // statistics
    public ListView<StatisticsEngine.AuthorData> favoriteAuthorsListView;
    public ListView<StatisticsEngine.GenreData> favoriteGenresListView;
    public ListView<Book> recommendedBooksListView;

    // save/restore
    public TextField fileNameTextField;
    public Text saveRestoreStatusText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // add the appropriate values to dropdowns.
        createBookGenreComboBox.getItems().addAll(Genre.values());
        createBookFolderComboBox.getItems().addAll(BookFolder.values());

        editBookGenreComboBox.getItems().addAll(Genre.values());
        editBookFolderComboBox.getItems().addAll(BookFolder.values());

        // register the validation failure text fields with the error synchronizer.
        createBookErrorSynchronizer.connectField(Book.Field.NAME, createBookNameValidationFailureText);
        createBookErrorSynchronizer.connectField(Book.Field.PAGE_COUNT, createBookPageCountValidationFailureText);
        createBookErrorSynchronizer.connectField(Book.Field.GENRE, createBookGenreValidationFailureText);
        createBookErrorSynchronizer.connectField(Book.Field.AUTHOR, createBookAuthorNameValidationFailureText);
        createBookErrorSynchronizer.connectField(Book.Field.PUBLISHED_DATE, createBookPublishedAtValidationFailureText);

        editBookErrorSynchronizer.connectField(Book.Field.NAME, editBookNameValidationFailureText);
        editBookErrorSynchronizer.connectField(Book.Field.RATING, editBookRatingValidationFailureText);
        editBookErrorSynchronizer.connectField(Book.Field.PAGE_COUNT, editBookPageCountValidationFailureText);
        editBookErrorSynchronizer.connectField(Book.Field.CURRENT_PAGE, editBookPagesReadSoFarValidationFailureText);
        editBookErrorSynchronizer.connectField(Book.Field.GENRE, editBookGenreValidationFailureText);
        editBookErrorSynchronizer.connectField(Book.Field.AUTHOR, editBookAuthorNameValidationFailureText);
        editBookErrorSynchronizer.connectField(Book.Field.PUBLISHED_DATE, editBookPublishedAtValidationFailureText);

        // when a user switches to another book folder, hide the book information.
        bookFolderTab.getSelectionModel().selectedItemProperty().addListener((tab, oldTab, newTab) -> {
            if (!oldTab.equals(newTab)) {
                bookInfoAnchorPane.setVisible(false);
            }
        });

        // render recent activity/statistics when clicked.
        tabPane.getSelectionModel().selectedItemProperty().addListener((tab, oldTab, newTab) -> {
            if (newTab.equals(recentActivityTab)) renderRecentActivity();
            else if (newTab.equals(statisticsTab)) renderStatistics();
        });
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Sets the current tab to the add book tab.
    public void handleNewBookShortcutClicked() {
        tabPane.getSelectionModel().select(addBookTab);
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Sets the current tab to the view books tab.
    public void handleViewBooksShortcutClicked() {
        tabPane.getSelectionModel().select(viewBookTab);
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Sets the current tab to the view recent activity tab.
    public void handleViewRecentActivityShortcutClicked() {
        tabPane.getSelectionModel().select(recentActivityTab);
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Sets the current tab to the view statistics tab.
    public void handleViewStatisticsShortcutClicked() {
        tabPane.getSelectionModel().select(statisticsTab);
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Sets the current tab to the save/restore data tab.
    public void handleSaveRestoreDataShortcutClicked() {
        tabPane.getSelectionModel().select(saveRestoreDataTab);
    }

    // Requires: Nothing.
    // Modifies: createBookNameTextField, createBookAuthorNameTextField, createBookGenreComboBox,
    // createBookPublishedAtDatePicker, createBookPageCountTextField, createBookFolderComboBox, database
    // Effects: Adds a book to the database using the input values.
    public void handleAddBook() {
        // validate the input.
        BookBuilder builder = new BookBuilder();
        ValidationErrorList<Book.Field> errors = new ValidationErrorList<>();
        List<Book.Field> skippedFields = new ArrayList<>();

        builder.setName(createBookNameTextField.getText());
        builder.setAuthor(createBookAuthorNameTextField.getText());
        builder.setGenre(createBookGenreComboBox.getValue());
        builder.setPublishedDate(createBookPublishedAtDatePicker.getValue());

        try {
            int pageCount = Integer.parseInt(createBookPageCountTextField.getText());
            builder.setPageCount(pageCount);
        } catch (Exception unused) {
            skippedFields.add(Book.Field.PAGE_COUNT);
            errors.add(Book.Field.PAGE_COUNT, "Page count must be an integer");
        }

        // make sure the user supplied a folder.
        BookFolder folder = createBookFolderComboBox.getValue();
        boolean isFolderValid = folder != null;
        if (!isFolderValid) {
            createBookFolderValidationFailureText.setText("Book folder is required");
            createBookFolderValidationFailureText.setVisible(true);
        } else {
            createBookFolderValidationFailureText.setVisible(false);
        }

        Book book = builder.build();
        BookValidator.validate(book, errors, skippedFields);

        // sync the errors with those in the GUI.
        createBookErrorSynchronizer.sync(errors);
        if (errors.hasErrors() || !isFolderValid) return; // return early if there were validation errors.

        // add the book to our collection.
        database.addBookToList(folder, book);
        getListViewForFolder(folder).getItems().add(book);

        // everything's good, so clear the input fields to signify success.
        createBookNameTextField.clear();
        createBookAuthorNameTextField.clear();
        createBookGenreComboBox.getSelectionModel().clearSelection();
        createBookPublishedAtDatePicker.getEditor().clear();
        createBookPageCountTextField.clear();
        createBookFolderComboBox.getSelectionModel().clearSelection();
    }

    // Requires: Nothing.
    // Modifies: bookInfoAnchorPane.
    // Effects: Displays the book information for the currently selected book.
    public void handleSelectBook() {
        Book book = getSelectedBook();
        if (book == null) {
            // no selected book, so hide the book information.
            bookInfoAnchorPane.setVisible(false);
            return;
        }

        // make the book information visible.
        bookInfoAnchorPane.setVisible(true);
        refreshBookInfo();
    }

    // Requires: Nothing.
    // Modifies: editBookFolderValidationFailureText, bookInfoAnchorPane, currently selected book.
    // Effects: Edits the currently selected book's properties.
    public void handleEditBook() {
        Book book = getSelectedBook();
        if (book == null) return; // return early if there's no book selected.

        // validate the input.
        ValidationErrorList<Book.Field> errors = new ValidationErrorList<>();

        String newName = editBookNameTextField.getText();
        BookValidator.validateName(newName, errors);

        Genre newGenre = editBookGenreComboBox.getValue();
        BookValidator.validateGenre(newGenre, errors);

        String newAuthorName = editBookAuthorNameTextField.getText();
        BookValidator.validateAuthor(newAuthorName, errors);

        LocalDate newPublishedAt = editBookPublishedAtDatePicker.getValue();
        BookValidator.validatePublishedDate(newPublishedAt, errors);

        int newPageCount = 0;
        try {
            newPageCount = Integer.parseInt(editBookPageCountTextField.getText());
            BookValidator.validatePageCount(newPageCount, errors);
        } catch (Exception unused) {
            errors.add(Book.Field.PAGE_COUNT, "Page count must be an integer");
        }

        int newPagesReadSoFar = book.getCurrentPage();
        String pagesReadSoFarRaw = editBookPagesReadSoFarTextField.getText();
        if (!pagesReadSoFarRaw.isEmpty()) {
            try {
                newPagesReadSoFar = Integer.parseInt(pagesReadSoFarRaw);
                if (newPagesReadSoFar < book.getCurrentPage()) {
                    errors.add(Book.Field.CURRENT_PAGE, "Pages read so far must be greater than current page");
                } else if (newPagesReadSoFar > book.getPageCount()) {
                    errors.add(Book.Field.CURRENT_PAGE, "Pages read so far must be less than total page count");
                }
            } catch (Exception unused) {
                errors.add(Book.Field.CURRENT_PAGE, "Pages read so far must be an integer");
            }
        }

        int newRating = book.getRating() == null
                ? -1
                : book.getRating().getRating();
        String bookRatingRaw = editBookRatingTextField.getText();
        if (!bookRatingRaw.isEmpty()) {
            try {
                newRating = Integer.parseInt(bookRatingRaw);
                if (newRating < 0 || newRating > 5) {
                    errors.add(Book.Field.RATING, "Rating must be between zero and five");
                }
            } catch (Exception unused) {
                errors.add(Book.Field.RATING, "Rating must be an integer");
            }
        }

        // sync errors with those in the GUI.
        editBookErrorSynchronizer.sync(errors);
        if (errors.hasErrors()) return; // return early if there were validation errors.

        book.setName(newName);
        book.setGenre(newGenre);
        book.setAuthor(newAuthorName);
        book.setPublishedDate(newPublishedAt);
        book.setPageCount(newPageCount);
        if (newPagesReadSoFar != book.getCurrentPage()) book.setCurrentPage(newPagesReadSoFar);

        // -1 denotes an unset rating
        if (newRating != -1) {
            boolean shouldSetRating = book.getRating() == null // rating not set before
                    || book.getRating().getRating() != newRating; // rating changed
            if (shouldSetRating) book.setRating(newRating);
        }

        BookFolder newFolder = editBookFolderComboBox.getValue();
        if (newFolder == null) {
            editBookFolderValidationFailureText.setText("Book folder is required");
            editBookFolderValidationFailureText.setVisible(true);
            return;
        }
        editBookFolderValidationFailureText.setVisible(false);

        // move folder if needed
        if (newFolder != getSelectedFolder()) {
            database.moveBookToFolder(getSelectedFolder(), newFolder, book);
            getListViewForFolder(newFolder).getItems().add(book);
            getListViewForFolder(getSelectedFolder()).getItems().remove(book);

            // hide the book information, since the previously selected book was just moved
            bookInfoAnchorPane.setVisible(false);
        } else {
            refreshBookInfo();
            // refresh the list view in case the book name changed
            getListViewForFolder(newFolder).refresh();
        }
    }

    // Requires: Nothing.
    // Modifies: database, bookInfoAnchorPane
    // Effects: Deletes the currently selected book.
    public void handleDeleteBook() {
        BookFolder folder = getSelectedFolder();
        Book book = getSelectedBook();
        if (book == null) return;

        database.removeBookFromList(folder, book);
        getListViewForFolder(folder).getItems().remove(book);
        bookInfoAnchorPane.setVisible(false);
    }

    // Requires: Nothing.
    // Modifies: activityLogListView, readingActivityByWeekdayListView, weekdayWithMostActivityText, weekdayWithLeastActivityText
    // Effects: Renders the recent activity page with the newest information.
    public void renderRecentActivity() {
        List<ActivityTracker.ActivityLogEntry> recentActivity = activityTracker.getRecentActivity();
        activityLogListView.getItems().setAll(recentActivity);

        Instant now = Time.currentInstant();
        Instant oneWeekAgo = now.minus(Duration.ofDays(7));
        List<ActivityTracker.ReadingActivityEntry> readingActivityEntries = activityTracker.getReadingActivityByWeekdayBetween(oneWeekAgo, now);
        readingActivityByWeekdayListView.getItems().setAll(readingActivityEntries);

        ActivityTracker.ReadingActivityEntry readMostEntry = null;
        ActivityTracker.ReadingActivityEntry readLeastEntry = null;
        // perform a linear scan to determine the weekdays on which the last/most was read.
        for (ActivityTracker.ReadingActivityEntry entry : readingActivityEntries) {
            int pagesRead = entry.getPagesRead();
            if (readMostEntry == null || pagesRead > readMostEntry.getPagesRead()) readMostEntry = entry;
            if (readLeastEntry == null || pagesRead < readLeastEntry.getPagesRead()) readLeastEntry = entry;
        }

        String dayOfWeekName = readMostEntry.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CANADA);
        weekdayWithMostActivityText.setText(
                "You read the most on " + dayOfWeekName + " with " + readMostEntry.getPagesRead() + " page(s)."
        );
        weekdayWithMostActivityText.setVisible(true);

        // only show the weekday with least activity if it's not the same as the day with the most activity
        if (readMostEntry != readLeastEntry) {
            dayOfWeekName = readLeastEntry.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CANADA);
            weekdayWithLeastActivityText.setText(
                    "You read the least on " + dayOfWeekName + " with " + readLeastEntry.getPagesRead() + " page(s)."
            );
            weekdayWithLeastActivityText.setVisible(true);
        } else {
            weekdayWithLeastActivityText.setVisible(false);
        }
    }

    // Requires: Nothing.
    // Modifies: favoriteAuthorsListView, favoriteGenresListView, recommendedBooksListView
    // Effects: Renders the statistics page with the newest information.
    public void renderStatistics() {
        List<StatisticsEngine.AuthorData> favoriteAuthors = statisticsEngine.getAuthorsSortedByPopularity();
        favoriteAuthorsListView.getItems().setAll(favoriteAuthors);

        List<StatisticsEngine.GenreData> favoriteGenres = statisticsEngine.getGenreDataSortedByPopularity();
        favoriteGenresListView.getItems().setAll(favoriteGenres);

        List<Book> recommendedBooks = statisticsEngine.getUnreadBooksSortedByRating();
        recommendedBooksListView.getItems().setAll(recommendedBooks);
    }

    // Requires: Nothing.
    // Modifies: saveRestoreStatusText, fileNameTextField
    // EffectS: Saves the book collection to a file.
    public void saveToFile() {
        try {
            database.save(fileNameTextField.getText());
            saveRestoreStatusText.setText("Successfully saved!");
            fileNameTextField.setText("");
        } catch (IOException unused) {
            saveRestoreStatusText.setText("Error while saving, are you sure you have the right file name?");
        }
    }

    // Requires: Nothing.
    // Modifies: database, activityTracker, statisticsEngine, wantToReadListView, currentlyReadingListView,
    // alreadyReadListView, saveRestoreStatusText
    // Effects: Loads a book collection from a file.
    public void loadFromFile() {
        try {
            database = BookDatabase.fromFile(fileNameTextField.getText());
            activityTracker = new ActivityTracker(database);
            statisticsEngine = new StatisticsEngine(database);

            wantToReadListView.getItems().setAll(database.getBooksInFolder(BookFolder.WANT_TO_READ));
            currentlyReadingListView.getItems().setAll(database.getBooksInFolder(BookFolder.CURRENTLY_READING));
            alreadyReadListView.getItems().setAll(database.getBooksInFolder(BookFolder.ALREADY_READ));

            saveRestoreStatusText.setText("Successfully loaded books from file!");
        } catch (IOException unused) {
            saveRestoreStatusText.setText("Error while loading, are you sure you have the right file name?");
        }
    }

    // Requires: Nothing.
    // Modifies: pagesReadInPastWeekText, editBookNameTextField, editBookPageCountTextField, editBookGenreComboBox,
    // editBookAuthorNameTextField, editBookPublishedAtDatePicker, editBookRatingTextField, editBookFolderComboBox,
    // editBookPagesReadSoFarTextField
    // Effects: Refreshes the book information pane with the currently selected book.
    private void refreshBookInfo() {
        BookFolder folder = getSelectedFolder();
        Book book = getSelectedBook();

        if (folder == BookFolder.CURRENTLY_READING) {
            // NOTE: The +1 second here is to account for a quite inaccurate clock on Windows.
            // Explanation: refreshBookInfo() is called when a book is edited. Sometimes, users will
            // edit the page where they are currently at; so the message below should - in theory -
            // change. However, it actually wouldn't without the +1 second. The system clock on Windows is only precise
            // to ~10ms or so, meaning that 'now' would be the same time as when the progress update was added.
            // Thus, one could observe that the count below was slightly inaccurate without the +1 second.
            Instant now = Time.currentInstant().plus(Duration.ofSeconds(1));
            Instant oneWeekAgo = now.minus(Duration.ofDays(7));
            int pagesReadInPastWeek = book.getPagesReadBetween(oneWeekAgo, now);
            pagesReadInPastWeekText.setText("In the past week, you read " + pagesReadInPastWeek + " page(s) of this book");
            pagesReadInPastWeekText.setVisible(true);
        } else {
            // pages read in the past week only applies to books which the user is currently reading
            pagesReadInPastWeekText.setVisible(false);
        }

        editBookNameTextField.setText(book.getName());
        editBookPageCountTextField.setText(Integer.toString(book.getPageCount()));
        editBookGenreComboBox.setValue(book.getGenre());
        editBookAuthorNameTextField.setText(book.getAuthor());
        editBookPublishedAtDatePicker.setValue(book.getPublishedDate());
        editBookRatingTextField.setText(book.getRating() == null ? "" : Integer.toString(book.getRating().getRating()));
        editBookFolderComboBox.setValue(getSelectedFolder());

        if (folder == BookFolder.CURRENTLY_READING) {
            editBookPagesReadSoFarTextField.setText(Integer.toString(book.getCurrentPage()));
            editBookPagesReadSoFarTextField.setDisable(false);
        } else {
            // "pages read so far" is a field that is only editable for books in the "currently reading"
            // folder.
            editBookPagesReadSoFarTextField.setDisable(true);
        }
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Returns the currently selected book.
    private Book getSelectedBook() {
        BookFolder folder = getSelectedFolder();
        if (folder == null) return null;
        return getListViewForFolder(folder).getSelectionModel().getSelectedItem();
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Returns the currently selected folder.
    private BookFolder getSelectedFolder() {
        Tab selectedTab = bookFolderTab.getSelectionModel().getSelectedItem();
        if (selectedTab == null) return null;
        if (selectedTab.equals(wantToReadTab)) return BookFolder.WANT_TO_READ;
        if (selectedTab.equals(currentlyReadingTab)) return BookFolder.CURRENTLY_READING;
        return BookFolder.ALREADY_READ;
    }

    // Requires: BookFolder folder - book folder to get the list view of.
    // Modifies: Nothing.
    // Effects: Returns the list view corresponding to the book folder provided.
    private ListView<Book> getListViewForFolder(BookFolder folder) {
        switch (folder) {
            case WANT_TO_READ:
                return wantToReadListView;
            case CURRENTLY_READING:
                return currentlyReadingListView;
            case ALREADY_READ:
                return alreadyReadListView;
            default:
                throw new IllegalArgumentException("Unknown book folder type.");
        }
    }
}
