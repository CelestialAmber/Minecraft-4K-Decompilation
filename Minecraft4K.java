import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class Minecraft4K extends Frame implements Runnable {
   private int[] M = new int[32767];
   int[] blockData = new int[262144];
   int[] textureData = new int[12288];
   int[] pixels;
   Random random = new Random();
   int width = 214, height = 120;



    public static void main(String[] args){
        Minecraft4K m = new Minecraft4K();
        m.setSize(856, 480);
        m.setTitle("Minecraft 4K");
        m.setVisible(true);
        m.run();
    }

    public void GenerateWorld(){
        random.setSeed(18295169L);
        for (int i = 0; i < 262144; i++) {
            blockData[i] = ((i / 64 % 64 > 32 + random.nextInt(8)) ? (random.nextInt(8) + 1) : 0);
        }
    }

    public void GenerateTextures(){
        for (int i = 1; i < 16; i++) {
            int br = 255 - random.nextInt(96);
            for (int y = 0; y < 48; y++) {
                for (int x = 0; x < 16; x++) {
                    int color = 0x966C4A;

                    if (i == 4) {
                        color = 0x7F7F7F;
                    }

                    if (i != 4 || random.nextInt(3) == 0) {
                        br = 255 - random.nextInt(96);
                    }

                    if (i == 1 && y < (x * x * 3 + x * 81 >> 2 & 0x3) + 18) {
                        color = 0x6AAA40;
                    }
                    else if (i == 1 && y < (x * x * 3 + x * 81 >> 2 & 0x3) + 19) {
                        br = br * 2 / 3;
                    }

                    if (i == 7) {
                        color = 0x675231;
                        if (x > 0 && x < 15 && ((y > 0 && y < 15) || (y > 32 && y < 47))) {
                            color = 0xBC9862;
                            int xd = x - 7;
                            int yd = (y & 0xF) - 7;
                            if (xd < 0) {
                                xd = 1 - xd;
                            }
                            if (yd < 0) {
                                yd = 1 - yd;
                            }
                            if (yd > xd) {
                                xd = yd;
                            }
                            br = 196 - random.nextInt(32) + xd % 3 * 32;
                        }
                        else if (random.nextInt(2) == 0) {
                            br = br * (150 - (x & 0x1) * 100) / 100;
                        }
                    }

                    if (i == 5) {
                        color = 0xB53A15;
                        if ((x + y / 4 * 4) % 8 == 0 || y % 4 == 0) {
                            color = 0xBCAFA5;
                        }
                    }

                    int brr = br;

                    if (y >= 32) {
                        brr /= 2;
                    }

                    if (i == 8) {
                        color = 5298487;
                        if (random.nextInt(2) == 0) {
                            color = 0;
                            brr = 255;
                        }
                    }

                    int r = (color >> 16 & 0xFF) * brr / 255;
                    int g = (color >> 8 & 0xFF) * brr / 255;
                    int b = (color & 0xFF) * brr / 255;
                    int pixelColor = r << 16 | g << 8 | b;

                    textureData[x + y * 16 + i * 256 * 3] = pixelColor;
                }
             }
          }
    }



   @Override
   public void run() {
      try {
          BufferedImage image = new BufferedImage(width, height, 1);
          pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

          GenerateWorld();
          GenerateTextures();

          float xPos = 96.5f;
          float yPos = 65.0f;
          float zPos = 96.5f;
          float velocityX = 0.0f;
          float velocityY = 0.0f;
          float velocityZ = 0.0f;
          long currentTimeMilliseconds = System.currentTimeMillis();
          int selectedBlockPosIndex = -1;
          int placeBlockOffset = 0;
          float xRot = 0.0f;
          float yRot = 0.0f;

          while (true) {
              float xSin = (float)Math.sin(xRot);
              float xCos = (float)Math.cos(xRot);
              float ySin = (float)Math.sin(yRot);
              float yCos = (float)Math.cos(yRot);

          Label_1192:
              while (true) {
                  while (System.currentTimeMillis() - currentTimeMilliseconds > 10) {
                      if (this.M[2] > 0) {
                          float mouseXPos = (this.M[2] - 428) / 214.0f * 2.0f;
                          float mouseYPos = (this.M[3] - 240) / 120.0f * 2.0f;
                          float mouseDistance = (float)Math.sqrt(mouseXPos * mouseXPos + mouseYPos * mouseYPos) - 1.2f;

                          System.out.println("Mouse position: (" + mouseXPos + ", " + mouseYPos + "), mouse distance: " + mouseDistance);

                          if (mouseDistance < 0.0f) {
                              mouseDistance = 0.0f;
                          }
                          if (mouseDistance > 0.0f) {
                              xRot += mouseXPos * mouseDistance / 400.0f;
                              yRot -= mouseYPos * mouseDistance / 400.0f;
                              if (yRot < -1.57f) {
                                  yRot = -1.57f;
                              }
                              if (yRot > 1.57f) {
                                  yRot = 1.57f;
                              }
                          }
                      }

                      currentTimeMilliseconds += 10;

                      float movementX = (this.M[100] - this.M[97]) * 0.02f; //checks for A/D keys
                      float movementY = (this.M[119] - this.M[115]) * 0.02f; //checks for W/S keys

                      velocityX *= 0.5f;
                      velocityY *= 0.99f;
                      velocityZ *= 0.5f;
                      velocityX += xSin * movementY + xCos * movementX;
                      velocityZ += xCos * movementY - xSin * movementX;
                      velocityY += 0.003f;

                      for (int i = 0; i < 3; i++) {
                          float newXPos = xPos + velocityX * ((i + 0) % 3 / 2);
                          float newYPos = yPos + velocityY * ((i + 1) % 3 / 2);
                          float newZPos = zPos + velocityZ * ((i + 2) % 3 / 2);
                          int index = 0;

                          while (index < 12) {
                              int blockX = (int)(newXPos + (index >> 0 & 0x1) * 0.6f - 0.3f) - 64;
                              int blockY = (int)(newYPos + ((index >> 2) - 1) * 0.8f + 0.65f) - 64;
                              int blockZ = (int)(newZPos + (index >> 1 & 0x1) * 0.6f - 0.3f) - 64;
                              if (blockX < 0 || blockY < 0 || blockZ < 0 || blockX >= 64 || blockY >= 64 || blockZ >= 64 || blockData[blockX + blockY * 64 + blockZ * 4096] > 0) {
                                  if (i != 1) {
                                      continue Label_1192;
                                  }
                                  if (this.M[32] > 0 && velocityY > 0.0f) {
                                      this.M[32] = 0;
                                      velocityY = -0.1f;
                                      continue Label_1192;
                                  }
                                  velocityY = 0.0f;
                                  continue Label_1192;
                              }
                              else {
                                  index++;
                              }
                          }

                          xPos = newXPos;
                          yPos = newYPos;
                          zPos = newZPos;
                      }
                  }
                  break;
              }
              int uvX = 0;
              int uvY = 0;

              if (this.M[1] > 0 && selectedBlockPosIndex > 0) {
                  blockData[selectedBlockPosIndex] = 0;
                  this.M[1] = 0;
              }

              if (this.M[0] > 0 && selectedBlockPosIndex > 0) {
                  blockData[selectedBlockPosIndex + placeBlockOffset] = 1;
                  this.M[0] = 0;
              }

              for (int i = 0; i < 12; ++i) {
                  int i15 = (int)(xPos + (i >> 0 & 0x1) * 0.6f - 0.3f) - 64;
                  int i16 = (int)(yPos + ((i >> 2) - 1) * 0.8f + 0.65f) - 64;
                  int i17 = (int)(zPos + (i >> 1 & 0x1) * 0.6f - 0.3f) - 64;
                  if (i15 >= 0 && i16 >= 0 && i17 >= 0 && i15 < 64 && i16 < 64 && i17 < 64) {
                      blockData[i15 + i16 * 64 + i17 * 4096] = 0;
                  }
              }

              int tempBlockPosIndex = -1;
              for (int x = 0; x < 214; x++) {
                  float ___xd = (x - 107) / 90.0f;
                  for (int y = 0; y < 120; y++) {
                      float __yd = (y - 60) / 90.0f;
                      float __zd = 1.0f;
                      
                      float ___zd = __zd * yCos + __yd * ySin;
                      float _yd = __yd * yCos - __zd * ySin;
                      float _xd = ___xd * xCos + ___zd * xSin;
                      float _zd = ___zd * xCos - ___xd * xSin;

                      int col = 0;
                      int br = 255;
                      double closest = 20.0;
                      float f26 = 5.0f;

                      for (int d = 0; d < 3; d++) {
                          float dimLength = _xd;
                          if (d == 1) {
                              dimLength = _yd;
                          }
                          if (d == 2) {
                              dimLength = _zd;
                          }
                          float ll = 1.0f / ((dimLength < 0.0f) ? (-dimLength) : dimLength);
                          float xd = _xd * ll;
                          float yd = _yd * ll;
                          float zd = _zd * ll;

                          float f32 = xPos - (int)xPos;
                          if (d == 1) f32 = yPos - (int)yPos;
                          if (d == 2)  f32 = zPos - (int)zPos;

                          if (dimLength > 0.0f) {
                              f32 = 1.0f - f32;
                          }

                          float dist = ll * f32;
                          float xp = xPos + xd * f32;
                          float yp = yPos + yd * f32;
                          float zp = zPos + zd * f32;

                          if (dimLength < 0.0f) {
                              if (d == 0) xp--;
                              if (d == 1) yp--;
                              if (d == 2) zp--;
                          }

                          while (dist < closest) {
                              int blockX = (int)xp - 64;
                              int blockY = (int)yp - 64;
                              int blockZ = (int)zp - 64;

                              if (blockX < 0 || blockY < 0 || blockZ < 0 || blockX >= 64 || blockY >= 64) {
                                  break;
                              }
                              if (blockZ >= 64) {
                                  break;
                              }

                              int blockPosIndex = blockX + blockY * 64 + blockZ * 4096;
                              int blockType = blockData[blockPosIndex];

                              if (blockType > 0) {
                                  uvX = ((int)((xp + zp) * 16.0f) & 0xF);
                                  uvY = ((int)(yp * 16.0f) & 0xF) + 16;

                                  if (d == 1) {
                                      uvX = ((int)(xp * 16.0f) & 0xF);
                                      uvY = ((int)(zp * 16.0f) & 0xF);
                                      if (yd < 0.0f) uvY += 32;
                                  }

                                  int cc = 0xFFFFFF;

                                  if (blockPosIndex != selectedBlockPosIndex || (uvX > 0 && uvY % 16 > 0 && uvX < 15 && uvY % 16 < 15)) {
                                      cc = textureData[uvX + uvY * 16 + blockType * 256 * 3];
                                  }

                                  if (dist < f26 && x == this.M[2] / 4 && y == this.M[3] / 4) {
                                      tempBlockPosIndex = blockPosIndex;
                                      placeBlockOffset = 1;
                                      if (dimLength > 0.0f) {
                                          placeBlockOffset = -1;
                                      }
                                      placeBlockOffset <<= 6 * d;
                                      f26 = dist;
                                  }
                                  if (cc > 0) {
                                      col = cc;
                                      br = 255 - (int)(dist / 20.0f * 255.0f);
                                      br = br * (255 - (d + 2) % 3 * 50) / 255;
                                      closest = dist;
                                  }
                              }

                              xp += xd;
                              yp += yd;
                              zp += zd;
                              dist += ll;
                          }
                      }
                      int r = (col >> 16 & 0xFF) * br / 255;
                      int g = (col >> 8 & 0xFF) * br / 255;
                      int b = (col & 0xFF) * br / 255;
                      pixels[x + y * 214] = (r << 16 | g << 8 | b);
                  }
              }
              selectedBlockPosIndex = tempBlockPosIndex;
              Thread.sleep(2L);
              if (!this.isActive()) break;
              this.getGraphics().drawImage(image, 0, 0, 856, 480, (ImageObserver)null);
          }
      }
      catch (Exception localException) {}
  }



  
  @Override
  public boolean handleEvent(final Event event) {
      int i = 0;
      switch (event.id) {
          case Event.KEY_PRESS: {
              i = 1;
          }
          case Event.KEY_RELEASE: {
              this.M[event.key] = i;
              break;
          }
          case Event.MOUSE_DOWN: {
              i = 1;
              this.M[2] = event.x;
              this.M[3] = event.y;
              System.out.println("a");
          }
          case Event.MOUSE_UP: {
              if ((event.modifiers & 0x4) > 0) {
                  this.M[1] = i;
                  break;
              }
              this.M[0] = i;
              break;
          }
          case Event.MOUSE_MOVE:
          case Event.MOUSE_DRAG: {
              this.M[2] = event.x;
              this.M[3] = event.y;
              break;
          }
          case Event.MOUSE_EXIT: {
              this.M[2] = 0;
              break;
          }
      }
      return true;
  }
}
