from PIL import Image
import numpy as np

def white_to_transparent(image_path, output_path=None, tolerance=0):
    """
    将图片中的白色像素转换为透明
    
    参数:
    - image_path: 输入图片路径
    - output_path: 输出图片路径（可选）
    - tolerance: 颜色容差，0-255，允许接近白色的像素也被转换
    """
    # 打开图片
    img = Image.open(image_path)
    print(f"图片模式: {img.mode}")
    # 确保图片有透明度通道（转换为RGBA模式）
    if img.mode != 'RGBA':
        img = img.convert('RGBA')
    
    # 获取图片数据
    data = np.array(img)
    
    # 定义白色的RGB值
    white_threshold = 255 - tolerance
    
    # 将白色或接近白色的像素的alpha通道设置为0（透明）
    # 找到所有RGB值都大于阈值的像素
    white_pixels = (data[:, :, 0] > white_threshold) & \
                   (data[:, :, 1] > white_threshold) & \
                   (data[:, :, 2] > white_threshold)
    
    # 将这些像素的alpha值设为0
    data[white_pixels, 3] = 0
    
    # 创建新图片
    transparent_img = Image.fromarray(data)
    
    # 保存或显示结果
    if output_path:
        transparent_img.save(output_path, format='PNG')  # PNG支持透明度
        print(f"图片已保存至: {output_path}")
    
    return transparent_img

# 使用示例
if __name__ == "__main__":
    # 方法1：直接使用函数
    # transparent_image = white_to_transparent(
    #     "input.png",  # 输入图片路径
    #     "output.png",  # 输出图片路径（可选）
    #     tolerance=10   # 容差，可调整（0表示只转换纯白色）
    # )
    
    # 方法2：显示结果（如果需要预览）
    # transparent_image.show()
    
    # 方法3：处理目录中的所有图片
    import os
    input_folder = "."
    output_folder = "."
    for file in os.listdir(input_folder):
        if file.endswith((".png", ".jpg", ".jpeg")):
            white_to_transparent(
                f"{input_folder}/{file}",
                f"{output_folder}/{os.path.splitext(file)[0]}_transparent.png"
            )