import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class Minecraft4K extends Frame implements Runnable {
   private int[] M = new int[32767];



   public static void main(String[] args){
      Minecraft4K m = new Minecraft4K();
      m.setSize(856, 480);
      m.setTitle("Minecraft 4K");
      m.setVisible(true);
      m.run();
   }



   @Override
   public void run() {
      try {
          final Random localRandom = new Random();
          final BufferedImage localBufferedImage = new BufferedImage(214, 120, 1);
          final int[] arrayOfInt1 = ((DataBufferInt)localBufferedImage.getRaster().getDataBuffer()).getData();
          final int[] arrayOfInt2 = new int[262144];
          localRandom.setSeed(18295169L);

          for (int i = 0; i < 262144; i++) {
              arrayOfInt2[i] = ((i / 64 % 64 > 32 + localRandom.nextInt(8)) ? (localRandom.nextInt(8) + 1) : 0);
          }

          final int[] arrayOfInt3 = new int[12288];

          for (int j = 1; j < 16; j++) {
              int k = 255 - localRandom.nextInt(96);
              for (int m = 0; m < 48; m++) {
                  for (int n = 0; n < 16; n++) {
                      int i2 = 9858122;
                      if (j == 4) {
                          i2 = 8355711;
                      }
                      if (j != 4 || localRandom.nextInt(3) == 0) {
                          k = 255 - localRandom.nextInt(96);
                      }
                      if (j == 1 && m < (n * n * 3 + n * 81 >> 2 & 0x3) + 18) {
                          i2 = 6990400;
                      }
                      else if (j == 1 && m < (n * n * 3 + n * 81 >> 2 & 0x3) + 19) {
                          k = k * 2 / 3;
                      }
                      if (j == 7) {
                          i2 = 6771249;
                          if (n > 0 && n < 15 && ((m > 0 && m < 15) || (m > 32 && m < 47))) {
                              i2 = 12359778;
                              int i3 = n - 7;
                              int i4 = (m & 0xF) - 7;
                              if (i3 < 0) {
                                  i3 = 1 - i3;
                              }
                              if (i4 < 0) {
                                  i4 = 1 - i4;
                              }
                              if (i4 > i3) {
                                  i3 = i4;
                              }
                              k = 196 - localRandom.nextInt(32) + i3 % 3 * 32;
                          }
                          else if (localRandom.nextInt(2) == 0) {
                              k = k * (150 - (n & 0x1) * 100) / 100;
                          }
                      }
                      if (j == 5) {
                          i2 = 11876885;
                          if ((n + m / 4 * 4) % 8 == 0 || m % 4 == 0) {
                              i2 = 12365733;
                          }
                      }
                      int i3 = k;
                      if (m >= 32) {
                          i3 /= 2;
                      }
                      if (j == 8) {
                          i2 = 5298487;
                          if (localRandom.nextInt(2) == 0) {
                              i2 = 0;
                              i3 = 255;
                          }
                      }
                      int i4 = (i2 >> 16 & 0xFF) * i3 / 255 << 16 | (i2 >> 8 & 0xFF) * i3 / 255 << 8 | (i2 & 0xFF) * i3 / 255;
                      arrayOfInt3[n + m * 16 + j * 256 * 3] = i4;
                  }
               }
            }

          float f1 = 96.5f;
          float f2 = 65.0f;
          float f3 = 96.5f;
          float f4 = 0.0f;
          float f5 = 0.0f;
          float f6 = 0.0f;
          long l = System.currentTimeMillis();
          int i5 = -1;
          int i6 = 0;
          float f7 = 0.0f;
          float f8 = 0.0f;

          while (true) {
              final float f9 = (float)Math.sin(f7);
              final float f10 = (float)Math.cos(f7);
              final float f11 = (float)Math.sin(f8);
              final float f12 = (float)Math.cos(f8);

          Label_1192:
              while (true) {
                  while (System.currentTimeMillis() - l > 10L) {
                      if (this.M[2] > 0) {
                          final float f13 = (this.M[2] - 428) / 214.0f * 2.0f;
                          final float f14 = (this.M[3] - 240) / 120.0f * 2.0f;
                          float f15 = (float)Math.sqrt(f13 * f13 + f14 * f14) - 1.2f;
                          if (f15 < 0.0f) {
                              f15 = 0.0f;
                          }
                          if (f15 > 0.0f) {
                              f7 += f13 * f15 / 400.0f;
                              f8 -= f14 * f15 / 400.0f;
                              if (f8 < -1.57f) {
                                  f8 = -1.57f;
                              }
                              if (f8 > 1.57f) {
                                  f8 = 1.57f;
                              }
                          }
                      }

                      l += 10L;
                      float f13 = 0.0f;
                      float f14 = 0.0f;
                      f14 += (this.M[119] - this.M[115]) * 0.02f;
                      f13 += (this.M[100] - this.M[97]) * 0.02f;
                      f4 *= 0.5f;
                      f5 *= 0.99f;
                      f6 *= 0.5f;
                      f4 += f9 * f14 + f10 * f13;
                      f6 += f10 * f14 - f9 * f13;
                      f5 += 0.003f;

                      for (int i7 = 0; i7 < 3; i7++) {
                          final float f16 = f1 + f4 * ((i7 + 0) % 3 / 2);
                          final float f17 = f2 + f5 * ((i7 + 1) % 3 / 2);
                          final float f18 = f3 + f6 * ((i7 + 2) % 3 / 2);
                          int i8 = 0;

                          while (i8 < 12) {
                              final int i9 = (int)(f16 + (i8 >> 0 & 0x1) * 0.6f - 0.3f) - 64;
                              final int i10 = (int)(f17 + ((i8 >> 2) - 1) * 0.8f + 0.65f) - 64;
                              final int i11 = (int)(f18 + (i8 >> 1 & 0x1) * 0.6f - 0.3f) - 64;
                              if (i9 < 0 || i10 < 0 || i11 < 0 || i9 >= 64 || i10 >= 64 || i11 >= 64 || arrayOfInt2[i9 + i10 * 64 + i11 * 4096] > 0) {
                                  if (i7 != 1) {
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
                                  ++i8;
                              }
                          }

                          f1 = f16;
                          f2 = f17;
                          f3 = f18;
                      }
                  }
                  break;
              }
              int i12 = 0;
              int i13 = 0;

              if (this.M[1] > 0 && i5 > 0) {
                  arrayOfInt2[i5] = 0;
                  this.M[1] = 0;
              }

              if (this.M[0] > 0 && i5 > 0) {
                  arrayOfInt2[i5 + i6] = 1;
                  this.M[0] = 0;
              }

              for (int i14 = 0; i14 < 12; ++i14) {
                  final int i15 = (int)(f1 + (i14 >> 0 & 0x1) * 0.6f - 0.3f) - 64;
                  final int i16 = (int)(f2 + ((i14 >> 2) - 1) * 0.8f + 0.65f) - 64;
                  final int i17 = (int)(f3 + (i14 >> 1 & 0x1) * 0.6f - 0.3f) - 64;
                  if (i15 >= 0 && i16 >= 0 && i17 >= 0 && i15 < 64 && i16 < 64 && i17 < 64) {
                      arrayOfInt2[i15 + i16 * 64 + i17 * 4096] = 0;
                  }
              }

              float i18 = -1.0f;
              for (int i15 = 0; i15 < 214; i15++) {
                  final float f19 = (i15 - 107) / 90.0f;
                  for (int i17 = 0; i17 < 120; i17++) {
                      final float f20 = (i17 - 60) / 90.0f;
                      final float f21 = 1.0f;
                      final float f22 = f21 * f12 + f20 * f11;
                      final float f23 = f20 * f12 - f21 * f11;
                      final float f24 = f19 * f10 + f22 * f9;
                      final float f25 = f22 * f10 - f19 * f9;
                      int i19 = 0;
                      int i20 = 255;
                      double d = 20.0;
                      float f26 = 5.0f;

                      for (int i21 = 0; i21 < 3; i21++) {
                          float f27 = f24;
                          if (i21 == 1) {
                              f27 = f23;
                          }
                          if (i21 == 2) {
                              f27 = f25;
                          }
                          final float f28 = 1.0f / ((f27 < 0.0f) ? (-f27) : f27);
                          final float f29 = f24 * f28;
                          final float f30 = f23 * f28;
                          final float f31 = f25 * f28;
                          float f32 = f1 - (int)f1;

                          if (i21 == 1) {
                              f32 = f2 - (int)f2;
                          }
                          if (i21 == 2) {
                              f32 = f3 - (int)f3;
                          }
                          if (f27 > 0.0f) {
                              f32 = 1.0f - f32;
                          }

                          float f33 = f28 * f32;
                          float f34 = f1 + f29 * f32;
                          float f35 = f2 + f30 * f32;
                          float f36 = f3 + f31 * f32;

                          if (f27 < 0.0f) {
                              if (i21 == 0) {
                                  --f34;
                              }
                              if (i21 == 1) {
                                  --f35;
                              }
                              if (i21 == 2) {
                                  --f36;
                              }
                          }

                          while (f33 < d) {
                              final int i22 = (int)f34 - 64;
                              final int i23 = (int)f35 - 64;
                              final int i24 = (int)f36 - 64;

                              if (i22 < 0 || i23 < 0 || i24 < 0 || i22 >= 64 || i23 >= 64) {
                                  break;
                              }
                              if (i24 >= 64) {
                                  break;
                              }

                              final int i25 = i22 + i23 * 64 + i24 * 4096;
                              final int i26 = arrayOfInt2[i25];

                              if (i26 > 0) {
                                  i12 = ((int)((f34 + f36) * 16.0f) & 0xF);
                                  i13 = ((int)(f35 * 16.0f) & 0xF) + 16;

                                  if (i21 == 1) {
                                      i12 = ((int)(f34 * 16.0f) & 0xF);
                                      i13 = ((int)(f36 * 16.0f) & 0xF);
                                      if (f30 < 0.0f) {
                                          i13 += 32;
                                      }
                                  }

                                  int i27 = 16777215;

                                  if (i25 != i5 || (i12 > 0 && i13 % 16 > 0 && i12 < 15 && i13 % 16 < 15)) {
                                      i27 = arrayOfInt3[i12 + i13 * 16 + i26 * 256 * 3];
                                  }

                                  if (f33 < f26 && i15 == this.M[2] / 4 && i17 == this.M[3] / 4) {
                                      i18 = (float)i25;
                                      i6 = 1;
                                      if (f27 > 0.0f) {
                                          i6 = -1;
                                      }
                                      i6 <<= 6 * i21;
                                      f26 = f33;
                                  }
                                  if (i27 > 0) {
                                      i19 = i27;
                                      i20 = 255 - (int)(f33 / 20.0f * 255.0f);
                                      i20 = i20 * (255 - (i21 + 2) % 3 * 50) / 255;
                                      d = f33;
                                  }
                              }

                              f34 += f29;
                              f35 += f30;
                              f36 += f31;
                              f33 += f28;
                          }
                      }
                      int i21 = (i19 >> 16 & 0xFF) * i20 / 255;
                      final int i28 = (i19 >> 8 & 0xFF) * i20 / 255;
                      final int i29 = (i19 & 0xFF) * i20 / 255;
                      arrayOfInt1[i15 + i17 * 214] = (i21 << 16 | i28 << 8 | i29);
                  }
              }
              i5 = (int)i18;
              Thread.sleep(2L);
              if (!this.isActive()) break;
              this.getGraphics().drawImage(localBufferedImage, 0, 0, 856, 480, (ImageObserver)null);
          }
      }
      catch (Exception localException) {}
  }



  
  @Override
  public boolean handleEvent(final Event paramEvent) {
      int i = 0;
      switch (paramEvent.id) {
          case 401: {
              i = 1;
          }
          case 402: {
              this.M[paramEvent.key] = i;
              break;
          }
          case 501: {
              i = 1;
              this.M[2] = paramEvent.x;
              this.M[3] = paramEvent.y;
          }
          case 502: {
              if ((paramEvent.modifiers & 0x4) > 0) {
                  this.M[1] = i;
                  break;
              }
              this.M[0] = i;
              break;
          }
          case 503:
          case 506: {
              this.M[2] = paramEvent.x;
              this.M[3] = paramEvent.y;
              break;
          }
          case 505: {
              this.M[2] = 0;
              break;
          }
      }
      return true;
  }
}
