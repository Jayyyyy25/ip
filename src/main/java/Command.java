public abstract class Command {
    public Command() {

    }
    abstract void execute(TaskList tasks, Ui ui, Storage storage) throws RemyException;

    public boolean isRunning() {
        return true;
    }
}
