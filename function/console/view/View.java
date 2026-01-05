package function.console.view;

public abstract class View {
    public abstract void show();

    // 控制台清屏
    public void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public abstract void updata();

}

