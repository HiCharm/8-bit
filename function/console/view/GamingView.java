package function.console.view;

public class GamingView extends View{

    @Override
    public void show() {
        // 5*5格子+右侧信息显示面板
        System.out.println("--------------------------------");
        for (int i = 0; i < 5; i++) {
            System.out.print("| ");
            for (int j = 0; j < 5; j++) {
                System.out.print("■ ");
            }
            if (i == 0) {
                System.out.print("| 生命值: 100");
            } else if (i == 1) {
                System.out.print("| 分数: 1500");
            } else if (i == 2) {
                System.out.print("| 等级: 3");
            } else if (i == 3) {
                System.out.print("| 道具: 无");
            } else if (i == 4) {
                System.out.print("| 回合: 10");
            }
            System.out.println();
        }
    }

    @Override
    public void updata() {
        
    }
    
}
