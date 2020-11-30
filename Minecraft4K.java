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
          float f4 = 0.0f;
          float f5 = 0.0f;
          float f6 = 0.0f;
          long currentTimeMilliseconds = System.currentTimeMillis();
          int i5 = -1;
          int i6 = 0;
          float xRot = 0.0f;
          float yRot = 0.0f;

          while (true) {
              final float xSin = (float)Math.sin(xRot);
              final float xCos = (float)Math.cos(xRot);
              final float ySin = (float)Math.sin(yRot);
              final float yCos = (float)Math.cos(yRot);

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

                      f4 *= 0.5f;
                      f5 *= 0.99f;
                      f6 *= 0.5f;
                      f4 += xSin * movementY + xCos * movementX;
                      f6 += xCos * movementY - xSin * movementX;
                      f5 += 0.003f;

                      for (int i = 0; i < 3; i++) {
                          final float f16 = xPos + f4 * ((i + 0) % 3 / 2);
                          final float f17 = yPos + f5 * ((i + 1) % 3 / 2);
                          final float f18 = zPos + f6 * ((i + 2) % 3 / 2);
                          int index = 0;

                          while (index < 12) {
                              final int i9 = (int)(f16 + (index >> 0 & 0x1) * 0.6f - 0.3f) - 64;
                              final int i10 = (int)(f17 + ((index >> 2) - 1) * 0.8f + 0.65f) - 64;
                              final int i11 = (int)(f18 + (index >> 1 & 0x1) * 0.6f - 0.3f) - 64;
                              if (i9 < 0 || i10 < 0 || i11 < 0 || i9 >= 64 || i10 >= 64 || i11 >= 64 || blockData[i9 + i10 * 64 + i11 * 4096] > 0) {
                                  if (i != 1) {
                                      continue Label_1192;
                                  }
                                  if (this.M[32] > 0 && f5 > 0.0f) {
                                      this.M[32] = 0;
                                      f5 = -0.1f;
                                      continue Label_1192;
                                  }
                                  f5 = 0.0f;
                                  continue Label_1192;
                              }
                              else {
                                  index++;
                              }
                          }

                          xPos = f16;
                          yPos = f17;
                          zPos = f18;
                      }
                  }
                  break;
              }
              int i12 = 0;
              int i13 = 0;

              if (this.M[1] > 0 && i5 > 0) {
                  blockData[i5] = 0;
                  this.M[1] = 0;
              }

              if (this.M[0] > 0 && i5 > 0) {
                  blockData[i5 + i6] = 1;
                  this.M[0] = 0;
              }

              for (int i14 = 0; i14 < 12; ++i14) {
                  final int i15 = (int)(xPos + (i14 >> 0 & 0x1) * 0.6f - 0.3f) - 64;
                  final int i16 = (int)(yPos + ((i14 >> 2) - 1) * 0.8f + 0.65f) - 64;
                  final int i17 = (int)(zPos + (i14 >> 1 & 0x1) * 0.6f - 0.3f) - 64;
                  if (i15 >= 0 && i16 >= 0 && i17 >= 0 && i15 < 64 && i16 < 64 && i17 < 64) {
                      blockData[i15 + i16 * 64 + i17 * 4096] = 0;
                  }
              }

              float i18 = -1.0f;
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

                          if (d == 1) {
                              f32 = yPos - (int)yPos;
                          }
                          if (d == 2) {
                              f32 = zPos - (int)zPos;
                          }
                          if (dimLength > 0.0f) {
                              f32 = 1.0f - f32;
                          }

                          float dist = ll * f32;
                          float xp = xPos + xd * f32;
                          float yp = yPos + yd * f32;
                          float zp = zPos + zd * f32;

                          if (dimLength < 0.0f) {
                              if (d == 0) {
                                  xp--;
                              }
                              if (d == 1) {
                                  yp--;
                              }
                              if (d == 2) {
                                  zp--;
                              }
                          }

                          while (dist < closest) {
                              int i22 = (int)xp - 64;
                              int i23 = (int)yp - 64;
                              int i24 = (int)zp - 64;

                              if (i22 < 0 || i23 < 0 || i24 < 0 || i22 >= 64 || i23 >= 64) {
                                  break;
                              }
                              if (i24 >= 64) {
                                  break;
                              }

                              int i25 = i22 + i23 * 64 + i24 * 4096;
                              int i26 = blockData[i25];

                              if (i26 > 0) {
                                  i12 = ((int)((xp + zp) * 16.0f) & 0xF);
                                  i13 = ((int)(yp * 16.0f) & 0xF) + 16;

                                  if (d == 1) {
                                      i12 = ((int)(xp * 16.0f) & 0xF);
                                      i13 = ((int)(zp * 16.0f) & 0xF);
                                      if (yd < 0.0f) {
                                          i13 += 32;
                                      }
                                  }

                                  int cc = 0xFFFFFF;

                                  if (i25 != i5 || (i12 > 0 && i13 % 16 > 0 && i12 < 15 && i13 % 16 < 15)) {
                                      cc = textureData[i12 + i13 * 16 + i26 * 256 * 3];
                                  }

                                  if (dist < f26 && x == this.M[2] / 4 && y == this.M[3] / 4) {
                                      i18 = (float)i25;
                                      i6 = 1;
                                      if (dimLength > 0.0f) {
                                          i6 = -1;
                                      }
                                      i6 <<= 6 * d;
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
              i5 = (int)i18;
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
