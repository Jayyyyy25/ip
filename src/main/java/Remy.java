public class Remy {
    private Ui ui;
    private TaskList tasks;
    private Storage storage;

    public Remy(String filepath) {
        ui = new Ui();
        storage = new Storage(filepath);
        try {
            tasks = new TaskList(storage.load());
        } catch (RemyException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        ui.showLine();
        boolean isRunning = true;
        while (isRunning) {
            try {
                String userInput = ui.readComment();
                ui.showLine();
                Command c = Parser.parseCommand(userInput);
                c.execute(tasks, ui, storage);
                isRunning = c.isRunning();
            } catch (Exception e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) throws InvalidArgumentException {
        new Remy("./data/remy.txt").run();
    }
}