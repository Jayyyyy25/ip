package remy.command;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import remy.task.TaskList;
import remy.util.Storage;
import remy.util.Ui;

/**
 * Subclass of {@code Command} that list out all tasks in the list
 */
public class ListCommand extends Command {
    private int listingType;
    private LocalDate specifiedDate;

    /**
     * Constructor for list command
     * <p>
     *     Listing type for calling {@code ListCommand}
     *     <ul>
     *         <li>{@code 0} for listing all the tasks in the list</li>
     *         <li>{@code 1} for listing tasks on a specified date</li>
     *         <li>{@code 2} for listing tasks on upcoming 3 days</li>
     *     </ul>
     * </p>
     * @param listingType Type of listing
     * @param date Specified date for listing tasks, if applicable
     */
    public ListCommand(int listingType, LocalDate ... date) {
        if (listingType == 1) {
            specifiedDate = date[0];
        } else {
            specifiedDate = null;
        }
        this.listingType = listingType;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        List<String> list = new ArrayList<>();
        if (listingType == 0) {
            list = tasks.getListing(null, "");
        } else if (listingType == 1) {
            list = tasks.getListing(specifiedDate, "");
        } else if (listingType == 2) {
            LocalDate today = LocalDate.now(ZoneId.of("Asia/Singapore"));
            list = tasks.getListing(today, "");
            list.addAll(tasks.getListing(today.plusDays(1), ""));
            list.addAll(tasks.getListing(today.plusDays(2), ""));
        } else {
            assert false : "Unreachable kind: listing type will now have a value other than 0, 1, 2";
        }
        return ui.showListing(list, listingType);
    }
}
