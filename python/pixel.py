import pygame
import sys
import os

# 初始化pygame
pygame.init()

# 颜色定义
COLORS = {
    '黑色': (0, 0, 0),
    '白色': (255, 255, 255),
    '红色': (255, 0, 0),
    '绿色': (0, 255, 0),
    '蓝色': (0, 0, 255),
    '黄色': (255, 255, 0),
    '紫色': (128, 0, 128),
    '青色': (0, 255, 255),
    '橙色': (255, 165, 0),
    '粉色': (255, 192, 203),
    '棕色': (139, 69, 19),
    '灰色': (128, 128, 128),
    '透明': (0, 0, 0, 0)  # 添加透明色块，使用RGBA格式
}

# 默认设置
CANVAS_SIZE = 16  # 16x16像素画布
PIXEL_SIZE = 30   # 每个像素的显示大小
TOOLBAR_HEIGHT = 180
COLOR_BUTTON_SIZE = 30
MARGIN = 10

# 计算窗口尺寸
WINDOW_WIDTH = CANVAS_SIZE * PIXEL_SIZE + 2 * MARGIN + 300  # 增加右侧面板宽度
WINDOW_HEIGHT = CANVAS_SIZE * PIXEL_SIZE + TOOLBAR_HEIGHT + 2 * MARGIN

# 创建窗口
screen = pygame.display.set_mode((WINDOW_WIDTH, WINDOW_HEIGHT))
pygame.display.set_caption("手绘小型像素画工具 - 支持自定义颜色")

# 初始化画布
canvas = [[COLORS['透明'] for _ in range(CANVAS_SIZE)] for _ in range(CANVAS_SIZE)]

# 当前选择的颜色
current_color = COLORS['黑色']

# 颜色输入框
color_input_boxes = {
    'r': {'rect': pygame.Rect(0, 0, 50, 30), 'text': '0', 'active': False},
    'g': {'rect': pygame.Rect(0, 0, 50, 30), 'text': '0', 'active': False},
    'b': {'rect': pygame.Rect(0, 0, 50, 30), 'text': '0', 'active': False},
    'hex': {'rect': pygame.Rect(0, 0, 100, 30), 'text': '000000', 'active': False}
}

# 绘制画布
def draw_canvas():
    # 绘制画布背景
    canvas_rect = pygame.Rect(MARGIN, MARGIN, CANVAS_SIZE * PIXEL_SIZE, CANVAS_SIZE * PIXEL_SIZE)
    pygame.draw.rect(screen, (240, 240, 240), canvas_rect)
    
    # 绘制网格和像素
    for y in range(CANVAS_SIZE):
        for x in range(CANVAS_SIZE):
            rect = pygame.Rect(
                MARGIN + x * PIXEL_SIZE,
                MARGIN + y * PIXEL_SIZE,
                PIXEL_SIZE, PIXEL_SIZE
            )
            color = canvas[y][x]
            # 检查颜色是否有alpha通道且为透明
            if len(color) == 4 and color[3] == 0:
                # 透明像素，只绘制网格线
                pass
            else:
                # 不透明像素，正常绘制
                pygame.draw.rect(screen, color, rect)
            pygame.draw.rect(screen, (200, 200, 200), rect, 1)  # 网格线

# 绘制工具栏
def draw_toolbar():
    toolbar_y = MARGIN + CANVAS_SIZE * PIXEL_SIZE + MARGIN
    
    # 工具栏背景
    toolbar_rect = pygame.Rect(0, toolbar_y - MARGIN, WINDOW_WIDTH, TOOLBAR_HEIGHT)
    pygame.draw.rect(screen, (220, 220, 220), toolbar_rect)
    
    # 左侧颜色选择区域
    left_panel_x = MARGIN
    
    # 颜色选择按钮
    color_keys = list(COLORS.keys())
    for i, color_name in enumerate(color_keys[:6]):  # 第一行6个颜色
        color = COLORS[color_name]
        btn_x = left_panel_x + i * (COLOR_BUTTON_SIZE + MARGIN)
        btn_y = toolbar_y
        
        # 绘制颜色按钮
        btn_rect = pygame.Rect(btn_x, btn_y, COLOR_BUTTON_SIZE, COLOR_BUTTON_SIZE)
        if color_name == '透明':
            # 绘制透明按钮的特殊处理
            pygame.draw.rect(screen, (240, 240, 240), btn_rect)  # 背景色
            # 绘制网格图案表示透明
            for grid_x in range(0, COLOR_BUTTON_SIZE, 5):
                for grid_y in range(0, COLOR_BUTTON_SIZE, 5):
                    if (grid_x // 5 + grid_y // 5) % 2 == 0:
                        pygame.draw.rect(screen, (200, 200, 200), pygame.Rect(btn_x + grid_x, btn_y + grid_y, 5, 5))
        else:
            pygame.draw.rect(screen, color, btn_rect)
        pygame.draw.rect(screen, (0, 0, 0), btn_rect, 2)
        
        # 如果当前颜色被选中，添加边框
        if color == current_color:
            pygame.draw.rect(screen, (255, 255, 0), btn_rect, 4)
        
        # 如果鼠标悬停在按钮上，显示颜色名称
        if btn_rect.collidepoint(pygame.mouse.get_pos()):
            font = pygame.font.SysFont('simHei', 18)
            text = font.render(color_name, True, (0, 0, 0))
            screen.blit(text, (btn_x, btn_y + COLOR_BUTTON_SIZE + 5))
    
    # 第二行颜色按钮
    for i, color_name in enumerate(color_keys[6:]):  # 第二行6个颜色
        color = COLORS[color_name]
        btn_x = left_panel_x + i * (COLOR_BUTTON_SIZE + MARGIN)
        btn_y = toolbar_y + COLOR_BUTTON_SIZE + MARGIN
        
        # 绘制颜色按钮
        btn_rect = pygame.Rect(btn_x, btn_y, COLOR_BUTTON_SIZE, COLOR_BUTTON_SIZE)
        if color_name == '透明':
            # 绘制透明按钮的特殊处理
            pygame.draw.rect(screen, (240, 240, 240), btn_rect)  # 背景色
            # 绘制网格图案表示透明
            for grid_x in range(0, COLOR_BUTTON_SIZE, 5):
                for grid_y in range(0, COLOR_BUTTON_SIZE, 5):
                    if (grid_x // 5 + grid_y // 5) % 2 == 0:
                        pygame.draw.rect(screen, (200, 200, 200), pygame.Rect(btn_x + grid_x, btn_y + grid_y, 5, 5))
        else:
            pygame.draw.rect(screen, color, btn_rect)
        pygame.draw.rect(screen, (0, 0, 0), btn_rect, 2)
        
        # 如果当前颜色被选中，添加边框
        if color == current_color:
            pygame.draw.rect(screen, (255, 255, 0), btn_rect, 4)
        
        # 如果鼠标悬停在按钮上，显示颜色名称
        if btn_rect.collidepoint(pygame.mouse.get_pos()):
            font = pygame.font.SysFont('simHei', 18)
            text = font.render(color_name, True, (0, 0, 0))
            screen.blit(text, (btn_x, btn_y + COLOR_BUTTON_SIZE + 5))
    
    # 右侧颜色输入区域
    right_panel_x = CANVAS_SIZE * PIXEL_SIZE + 3 * MARGIN
    
    # 标题
    font = pygame.font.SysFont('simHei', 24)
    title_text = font.render("自定义颜色", True, (0, 0, 0))
    screen.blit(title_text, (right_panel_x, toolbar_y))
    
    # RGB输入区域
    rgb_y = toolbar_y + 30
    
    # R输入框
    r_label = pygame.font.SysFont('simHei', 20).render("R:", True, (0, 0, 0))
    screen.blit(r_label, (right_panel_x, rgb_y + 8))
    
    color_input_boxes['r']['rect'] = pygame.Rect(right_panel_x + 20, rgb_y, 50, 30)
    draw_input_box(color_input_boxes['r'], "R")
    
    # G输入框
    g_label = pygame.font.SysFont('simHei', 20).render("G:", True, (0, 0, 0))
    screen.blit(g_label, (right_panel_x + 80, rgb_y + 8))
    
    color_input_boxes['g']['rect'] = pygame.Rect(right_panel_x + 100, rgb_y, 50, 30)
    draw_input_box(color_input_boxes['g'], "G")
    
    # B输入框
    b_label = pygame.font.SysFont('simHei', 20).render("B:", True, (0, 0, 0))
    screen.blit(b_label, (right_panel_x + 160, rgb_y + 8))
    
    color_input_boxes['b']['rect'] = pygame.Rect(right_panel_x + 180, rgb_y, 50, 30)
    draw_input_box(color_input_boxes['b'], "B")
    
    # 十六进制输入区域
    hex_y = rgb_y + 40
    
    hex_label = pygame.font.SysFont('simHei', 20).render("#:", True, (0, 0, 0))
    screen.blit(hex_label, (right_panel_x, hex_y + 8))
    
    color_input_boxes['hex']['rect'] = pygame.Rect(right_panel_x + 20, hex_y, 100, 30)
    draw_input_box(color_input_boxes['hex'], "#")
    
    # 应用RGB按钮
    apply_rgb_btn = pygame.Rect(right_panel_x + 130, hex_y, 60, 30)
    pygame.draw.rect(screen, (100, 150, 200), apply_rgb_btn)
    pygame.draw.rect(screen, (0, 0, 0), apply_rgb_btn, 2)
    apply_text = pygame.font.SysFont('simHei', 20).render("应用", True, (0, 0, 0))
    screen.blit(apply_text, (apply_rgb_btn.x + 18, apply_rgb_btn.y + 8))
    
    # 颜色预览
    preview_y = hex_y + 40
    preview_label = pygame.font.SysFont('simHei', 20).render("预览:", True, (0, 0, 0))
    screen.blit(preview_label, (right_panel_x, preview_y + 8))
    
    preview_rect = pygame.Rect(right_panel_x + 50, preview_y, 60, 30)
    pygame.draw.rect(screen, current_color, preview_rect)
    pygame.draw.rect(screen, (0, 0, 0), preview_rect, 2)
    
    # 显示当前RGB值
    rgb_text = pygame.font.SysFont('simHei', 18).render(
        f"RGB: {current_color[0]}, {current_color[1]}, {current_color[2]}", 
        True, (0, 0, 0)
    )
    screen.blit(rgb_text, (right_panel_x + 120, preview_y + 10))
    
    # 功能按钮区域
    btn_y = toolbar_y + 120
    
    # 保存按钮
    save_btn_rect = pygame.Rect(right_panel_x, btn_y, 80, 30)
    pygame.draw.rect(screen, (100, 200, 100), save_btn_rect)
    pygame.draw.rect(screen, (0, 0, 0), save_btn_rect, 2)
    save_text = font.render("保存", True, (0, 0, 0))
    screen.blit(save_text, (save_btn_rect.x + 25, save_btn_rect.y + 5))
    
    # 清除按钮
    clear_btn_rect = pygame.Rect(right_panel_x + 90, btn_y, 80, 30)
    pygame.draw.rect(screen, (200, 100, 100), clear_btn_rect)
    pygame.draw.rect(screen, (0, 0, 0), clear_btn_rect, 2)
    clear_text = font.render("清除", True, (0, 0, 0))
    screen.blit(clear_text, (clear_btn_rect.x + 25, clear_btn_rect.y + 5))
    
    # 拾色器按钮
    picker_btn_rect = pygame.Rect(right_panel_x + 180, btn_y, 80, 30)
    pygame.draw.rect(screen, (200, 150, 100), picker_btn_rect)
    pygame.draw.rect(screen, (0, 0, 0), picker_btn_rect, 2)
    picker_text = font.render("拾色器", True, (0, 0, 0))
    screen.blit(picker_text, (picker_btn_rect.x + 15, picker_btn_rect.y + 5))
    
    return apply_rgb_btn, save_btn_rect, clear_btn_rect, picker_btn_rect

# 绘制输入框
def draw_input_box(input_box, label):
    # 输入框背景
    color = (255, 255, 255) if not input_box['active'] else (200, 230, 255)
    pygame.draw.rect(screen, color, input_box['rect'])
    pygame.draw.rect(screen, (0, 0, 0), input_box['rect'], 2)
    
    # 输入文本
    font = pygame.font.SysFont('simHei', 24)
    text_surface = font.render(input_box['text'], True, (0, 0, 0))
    screen.blit(text_surface, (input_box['rect'].x + 5, input_box['rect'].y + 5))

# 保存画布为图像
def save_canvas():
    # 创建一个新的surface来保存画布内容，支持alpha通道
    save_surface = pygame.Surface((CANVAS_SIZE, CANVAS_SIZE), pygame.SRCALPHA)
    
    # 将颜色数据复制到surface
    for y in range(CANVAS_SIZE):
        for x in range(CANVAS_SIZE):
            color = canvas[y][x]
            # 确保颜色有alpha通道
            if len(color) == 3:
                save_surface.set_at((x, y), (*color, 255))  # 不透明
            else:
                save_surface.set_at((x, y), color)  # 保留alpha值
    
    # 放大图像
    scaled_surface = pygame.transform.scale(save_surface, (CANVAS_SIZE * 20, CANVAS_SIZE * 20))
    
    # 确保保存目录存在
    if not os.path.exists("res"):
        os.makedirs("res")
    
    # 生成文件名
    import datetime
    timestamp = datetime.datetime.now().strftime("%Y%m%d_%H%M%S")
    filename = f"res/{timestamp}.png"
    
    # 保存图像
    pygame.image.save(scaled_surface, filename)
    print(f"像素画已保存为 {filename}")
    
    # 显示保存成功的消息
    font = pygame.font.SysFont('simHei', 30)
    text = font.render("保存成功!", True, (0, 100, 0))
    text_rect = text.get_rect(center=(WINDOW_WIDTH // 2, WINDOW_HEIGHT // 2))
    
    screen.blit(text, text_rect)
    pygame.display.flip()
    pygame.time.delay(1000)  # 显示消息1秒钟

# 清除画布
def clear_canvas():
    for y in range(CANVAS_SIZE):
        for x in range(CANVAS_SIZE):
            canvas[y][x] = COLORS['透明']

# 从RGB更新十六进制输入框
def update_hex_from_rgb():
    try:
        r = int(color_input_boxes['r']['text']) if color_input_boxes['r']['text'] else 0
        g = int(color_input_boxes['g']['text']) if color_input_boxes['g']['text'] else 0
        b = int(color_input_boxes['b']['text']) if color_input_boxes['b']['text'] else 0
        
        # 确保RGB值在有效范围内
        r = max(0, min(255, r))
        g = max(0, min(255, g))
        b = max(0, min(255, b))
        
        # 转换为十六进制
        hex_color = f"{r:02x}{g:02x}{b:02x}"
        color_input_boxes['hex']['text'] = hex_color.upper()
    except ValueError:
        pass

# 从十六进制更新RGB输入框
def update_rgb_from_hex():
    try:
        hex_text = color_input_boxes['hex']['text'].lstrip('#')
        
        # 处理3位简写形式
        if len(hex_text) == 3:
            hex_text = ''.join([c*2 for c in hex_text])
        
        if len(hex_text) == 6:
            r = int(hex_text[0:2], 16)
            g = int(hex_text[2:4], 16)
            b = int(hex_text[4:6], 16)
            
            color_input_boxes['r']['text'] = str(r)
            color_input_boxes['g']['text'] = str(g)
            color_input_boxes['b']['text'] = str(b)
    except ValueError:
        pass

# 应用颜色输入
def apply_color_input():
    try:
        r = int(color_input_boxes['r']['text']) if color_input_boxes['r']['text'] else 0
        g = int(color_input_boxes['g']['text']) if color_input_boxes['g']['text'] else 0
        b = int(color_input_boxes['b']['text']) if color_input_boxes['b']['text'] else 0
        
        # 确保RGB值在有效范围内
        r = max(0, min(255, r))
        g = max(0, min(255, g))
        b = max(0, min(255, b))
        
        # 更新当前颜色
        global current_color
        current_color = (r, g, b)
        
        # 更新输入框显示
        color_input_boxes['r']['text'] = str(r)
        color_input_boxes['g']['text'] = str(g)
        color_input_boxes['b']['text'] = str(b)
        update_hex_from_rgb()
        
        return True
    except ValueError:
        return False

# 主循环
def main():
    global current_color
    
    clock = pygame.time.Clock()
    drawing = False
    color_picking = False
    
    # 初始化输入框的值
    color_input_boxes['r']['text'] = str(current_color[0])
    color_input_boxes['g']['text'] = str(current_color[1])
    color_input_boxes['b']['text'] = str(current_color[2])
    update_hex_from_rgb()
    
    while True:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()
            
            # 鼠标按下事件
            elif event.type == pygame.MOUSEBUTTONDOWN:
                mouse_x, mouse_y = pygame.mouse.get_pos()
                
                # 检查是否点击了画布区域
                canvas_left = MARGIN
                canvas_top = MARGIN
                canvas_right = canvas_left + CANVAS_SIZE * PIXEL_SIZE
                canvas_bottom = canvas_top + CANVAS_SIZE * PIXEL_SIZE
                
                if canvas_left <= mouse_x < canvas_right and canvas_top <= mouse_y < canvas_bottom:
                    if color_picking:
                        # 拾取颜色
                        pixel_x = (mouse_x - MARGIN) // PIXEL_SIZE
                        pixel_y = (mouse_y - MARGIN) // PIXEL_SIZE
                        
                        if 0 <= pixel_x < CANVAS_SIZE and 0 <= pixel_y < CANVAS_SIZE:
                            picked_color = canvas[pixel_y][pixel_x]
                            current_color = picked_color
                            
                            # 更新输入框
                            color_input_boxes['r']['text'] = str(picked_color[0])
                            color_input_boxes['g']['text'] = str(picked_color[1])
                            color_input_boxes['b']['text'] = str(picked_color[2])
                            update_hex_from_rgb()
                            
                            color_picking = False
                    else:
                        drawing = True
                        # 计算点击的像素坐标
                        pixel_x = (mouse_x - MARGIN) // PIXEL_SIZE
                        pixel_y = (mouse_y - MARGIN) // PIXEL_SIZE
                        
                        # 设置像素颜色
                        if 0 <= pixel_x < CANVAS_SIZE and 0 <= pixel_y < CANVAS_SIZE:
                            canvas[pixel_y][pixel_x] = current_color
                
                # 检查是否点击了颜色选择按钮
                toolbar_y = MARGIN + CANVAS_SIZE * PIXEL_SIZE + MARGIN
                color_keys = list(COLORS.keys())
                
                # 第一行颜色按钮
                for i, color_name in enumerate(color_keys[:6]):
                    btn_x = MARGIN + i * (COLOR_BUTTON_SIZE + MARGIN)
                    btn_y = toolbar_y
                    btn_rect = pygame.Rect(btn_x, btn_y, COLOR_BUTTON_SIZE, COLOR_BUTTON_SIZE)
                    
                    if btn_rect.collidepoint(mouse_x, mouse_y):
                        current_color = COLORS[color_name]
                        # 更新输入框
                        color_input_boxes['r']['text'] = str(current_color[0])
                        color_input_boxes['g']['text'] = str(current_color[1])
                        color_input_boxes['b']['text'] = str(current_color[2])
                        update_hex_from_rgb()
                
                # 第二行颜色按钮
                for i, color_name in enumerate(color_keys[6:]):
                    btn_x = MARGIN + i * (COLOR_BUTTON_SIZE + MARGIN)
                    btn_y = toolbar_y + COLOR_BUTTON_SIZE + MARGIN
                    btn_rect = pygame.Rect(btn_x, btn_y, COLOR_BUTTON_SIZE, COLOR_BUTTON_SIZE)
                    
                    if btn_rect.collidepoint(mouse_x, mouse_y):
                        current_color = COLORS[color_name]
                        # 更新输入框
                        color_input_boxes['r']['text'] = str(current_color[0])
                        color_input_boxes['g']['text'] = str(current_color[1])
                        color_input_boxes['b']['text'] = str(current_color[2])
                        update_hex_from_rgb()
                
                # 检查是否点击了输入框
                for key, box in color_input_boxes.items():
                    if box['rect'].collidepoint(mouse_x, mouse_y):
                        # 激活点击的输入框，禁用其他输入框
                        for k in color_input_boxes:
                            color_input_boxes[k]['active'] = (k == key)
                        break
                else:
                    # 如果没有点击任何输入框，则全部禁用
                    for box in color_input_boxes.values():
                        box['active'] = False
                
                # 检查是否点击了应用按钮
                if apply_rgb_btn.collidepoint(mouse_x, mouse_y):
                    apply_color_input()
                
                # 检查是否点击了保存按钮
                if save_btn_rect.collidepoint(mouse_x, mouse_y):
                    save_canvas()
                
                # 检查是否点击了清除按钮
                if clear_btn_rect.collidepoint(mouse_x, mouse_y):
                    clear_canvas()
                
                # 检查是否点击了拾色器按钮
                if picker_btn_rect.collidepoint(mouse_x, mouse_y):
                    color_picking = not color_picking
            
            # 鼠标释放事件
            elif event.type == pygame.MOUSEBUTTONUP:
                drawing = False
            
            # 鼠标移动事件（用于拖动绘制）
            elif event.type == pygame.MOUSEMOTION and drawing:
                mouse_x, mouse_y = pygame.mouse.get_pos()
                
                # 检查是否在画布区域内
                canvas_left = MARGIN
                canvas_top = MARGIN
                canvas_right = canvas_left + CANVAS_SIZE * PIXEL_SIZE
                canvas_bottom = canvas_top + CANVAS_SIZE * PIXEL_SIZE
                
                if canvas_left <= mouse_x < canvas_right and canvas_top <= mouse_y < canvas_bottom:
                    # 计算像素坐标
                    pixel_x = (mouse_x - MARGIN) // PIXEL_SIZE
                    pixel_y = (mouse_y - MARGIN) // PIXEL_SIZE
                    
                    # 设置像素颜色
                    if 0 <= pixel_x < CANVAS_SIZE and 0 <= pixel_y < CANVAS_SIZE:
                        canvas[pixel_y][pixel_x] = current_color
            
            # 键盘事件
            elif event.type == pygame.KEYDOWN:
                # 按ESC退出
                if event.key == pygame.K_ESCAPE:
                    pygame.quit()
                    sys.exit()
                # 按C清除画布
                elif event.key == pygame.K_c:
                    clear_canvas()
                # 按S保存画布
                elif event.key == pygame.K_s:
                    save_canvas()
                # 按P切换拾色器模式
                elif event.key == pygame.K_p:
                    color_picking = not color_picking
                # 按Enter应用颜色输入
                elif event.key == pygame.K_RETURN:
                    apply_color_input()
                # 处理文本输入
                else:
                    # 检查哪个输入框是活动的
                    for key, box in color_input_boxes.items():
                        if box['active']:
                            # 处理退格键
                            if event.key == pygame.K_BACKSPACE:
                                box['text'] = box['text'][:-1]
                            # 处理数字输入
                            elif event.unicode.isdigit() or (key == 'hex' and event.unicode.lower() in 'abcdef'):
                                if len(box['text']) < (6 if key == 'hex' else 3):
                                    box['text'] += event.unicode
                            
                            # 如果输入的是RGB值，同步更新十六进制
                            if key in ['r', 'g', 'b']:
                                update_hex_from_rgb()
                            # 如果输入的是十六进制，同步更新RGB
                            elif key == 'hex':
                                update_rgb_from_hex()
                            break
        
        # 绘制界面
        screen.fill((245, 245, 245))  # 背景色
        draw_canvas()
        apply_rgb_btn, save_btn_rect, clear_btn_rect, picker_btn_rect = draw_toolbar()
        
        # 显示说明文本
        font = pygame.font.SysFont('simHei', 16)
        instructions = [
            "使用说明:",
            "1. 点击颜色按钮选择颜色，或在右侧输入RGB或十六进制颜色",
            "2. 在画布上点击或拖动绘制像素",
            "3. 点击'拾色器'按钮或按P键，然后在画布上点击可以拾取颜色",
            "4. 点击'清除'按钮或按C键清除画布",
            "5. 点击'保存'按钮或按S键保存作品",
            "6. 按ESC键退出"
        ]
        
        for i, line in enumerate(instructions):
            text = font.render(line, True, (80, 80, 80))
            screen.blit(text, (10, WINDOW_HEIGHT - 110 + i * 15))
        
        # 显示拾色器模式状态
        if color_picking:
            picker_text = font.render("拾色器模式已激活 - 点击画布上的颜色", True, (200, 0, 0))
            screen.blit(picker_text, (WINDOW_WIDTH - 300, WINDOW_HEIGHT - 30))
        
        pygame.display.flip()
        clock.tick(60)

if __name__ == "__main__":
    main()