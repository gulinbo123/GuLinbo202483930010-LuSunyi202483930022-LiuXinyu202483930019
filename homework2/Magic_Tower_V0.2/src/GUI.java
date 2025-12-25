import java.awt.*;
import javax.swing.*;
public class GUI {
	GameData gameData;
	JFrame f;
	JLabel[][] b;
    // 新增↓ 迷你地图相关属性（直接加在原有属性后面）
    private JPanel miniMapPanel; // 迷你地图面板
    private final int MINI_MAP_SIZE = 200; // 迷你地图尺寸（200x200）
    private final int MINI_CELL_SIZE; // 迷你地图每个格子的尺寸（自动计算）
	GUI(GameData gameData) {
		this.gameData = gameData;
        // 新增↓ 计算迷你地图每个格子的尺寸（适配不同地图大小）
        MINI_CELL_SIZE = Math.min(MINI_MAP_SIZE / gameData.W, MINI_MAP_SIZE / gameData.H);

        f = new JFrame("Magic Tower");
        // 新增↓ 初始化迷你地图面板
        miniMapPanel = new JPanel() {
            // 重写paintComponent方法，绘制迷你地图
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawMiniMap(g); // 调用自定义绘制方法
            }
        };
        // 设置迷你地图面板的位置和大小（右上角）
        miniMapPanel.setBounds(gameData.W * 100 + 10, 10, MINI_MAP_SIZE, MINI_MAP_SIZE);
        miniMapPanel.setBackground(Color.LIGHT_GRAY); // 迷你地图背景色
        f.add(miniMapPanel); // 添加到主窗口

		b = new JLabel[gameData.H][gameData.W];
		for (int i = 0; i < gameData.H; i++) {
			for (int j = 0; j < gameData.W; j++) {
				b[i][j]=new JLabel();
				b[i][j].setBounds(j*100, i*100, 100, 100);
				f.add(b[i][j]);
			}
		}
		f.setSize(gameData.W*100 + MINI_MAP_SIZE + 20, gameData.H*100 + 40);
		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		refreshGUI();
	}
	public void refreshGUI()
	{
		for (int i = 0; i < gameData.H; i++) {
			for (int j = 0; j < gameData.W; j++) {
				Image scaledImage = chooseImage(gameData.map[gameData.currentLevel][i][j]);
				b[i][j].setIcon(new ImageIcon(scaledImage));
			}
		}
        miniMapPanel.repaint();
	}
	private static Image chooseImage(int index){
		ImageIcon[] icons = new ImageIcon[10];
		Image scaledImage;
		icons[0]= new ImageIcon("Wall.jpg");
		icons[1]= new ImageIcon("Floor.jpg");
		icons[2]= new ImageIcon("Key.jpg");
		icons[3]= new ImageIcon("Door.jpg");
		icons[4]= new ImageIcon("Stair.jpg");
		icons[5]= new ImageIcon("Exit.jpg");
		icons[6]= new ImageIcon("Hero.jpg");
		icons[7]= new ImageIcon("Potion.jpg");
		icons[8]= new ImageIcon("Monster.jpg");
        icons[9]= new ImageIcon("Floor.jpg");
		if(index>10)
			scaledImage = icons[7].getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		else if(index<0)
			scaledImage = icons[8].getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        else if(index >= icons.length) // 新增↓ 索引越界时默认地板
            scaledImage = icons[1].getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		else
			scaledImage = icons[index].getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		return scaledImage;
	}
    // 新增↓ 绘制迷你地图的核心方法
    private void drawMiniMap(Graphics g) {
        int[][] currentMap = gameData.map[gameData.currentLevel]; // 当前关卡地图
        int offsetX = (MINI_MAP_SIZE - gameData.W * MINI_CELL_SIZE) / 2; // X轴居中偏移
        int offsetY = (MINI_MAP_SIZE - gameData.H * MINI_CELL_SIZE) / 2; // Y轴居中偏移

        // 遍历地图，绘制每个格子
        for (int i = 0; i < gameData.H; i++) {
            for (int j = 0; j < gameData.W; j++) {
                int x = j * MINI_CELL_SIZE + offsetX; // 迷你地图中格子的X坐标
                int y = i * MINI_CELL_SIZE + offsetY; // 迷你地图中格子的Y坐标

                // 根据地图数值设置颜色
                switch (currentMap[i][j]) {
                    case 0: // 墙
                        g.setColor(Color.GRAY);
                        g.fillRect(x, y, MINI_CELL_SIZE, MINI_CELL_SIZE);
                        break;
                    case 1: // 地板
                        g.setColor(Color.WHITE);
                        g.fillRect(x, y, MINI_CELL_SIZE, MINI_CELL_SIZE);
                        break;
                    case 2: // 钥匙
                        g.setColor(Color.YELLOW);
                        g.fillRect(x, y, MINI_CELL_SIZE, MINI_CELL_SIZE);
                        break;
                    case 3: // 门
                        g.setColor(Color.BLUE);
                        g.fillRect(x, y, MINI_CELL_SIZE, MINI_CELL_SIZE);
                        break;
                    case 4: // 楼梯
                        g.setColor(Color.ORANGE);
                        g.fillRect(x, y, MINI_CELL_SIZE, MINI_CELL_SIZE);
                        break;
                    case 5: // 出口
                        g.setColor(Color.GREEN);
                        g.fillRect(x, y, MINI_CELL_SIZE, MINI_CELL_SIZE);
                        break;
                    case 6: // 英雄（先不画，最后画红色圆点，避免被覆盖）
                        break;
                    default:
                        if (currentMap[i][j] > 10) { // 药水
                            g.setColor(Color.PINK);
                            g.fillRect(x, y, MINI_CELL_SIZE, MINI_CELL_SIZE);
                        } else if (currentMap[i][j] < 0) { // 怪物
                            g.setColor(Color.BLACK);
                            g.fillRect(x, y, MINI_CELL_SIZE, MINI_CELL_SIZE);
                        } else { // 其他默认地板
                            g.setColor(Color.WHITE);
                            g.fillRect(x, y, MINI_CELL_SIZE, MINI_CELL_SIZE);
                        }
                }

                // 绘制格子边框（可选，让迷你地图更清晰）
                g.setColor(Color.DARK_GRAY);
                g.drawRect(x, y, MINI_CELL_SIZE, MINI_CELL_SIZE);
            }
        }

        // 最后绘制英雄（红色圆点，确保在最上层）
        int heroX = gameData.pY * MINI_CELL_SIZE + offsetX + MINI_CELL_SIZE/2;
        int heroY = gameData.pX * MINI_CELL_SIZE + offsetY + MINI_CELL_SIZE/2;
        int heroRadius = MINI_CELL_SIZE/2; // 圆点半径
        g.setColor(Color.RED);
        g.fillOval(heroX - heroRadius, heroY - heroRadius, heroRadius*2, heroRadius*2);
    }

}

