import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MandelbrotAscii {

    private static final char[] PALETTE = " .:-=+*#%@".toCharArray();

    public static void main(String[] args) {
        int width  = args.length > 0 ? parseInt(args[0], 120) : 120;
        int height = args.length > 1 ? parseInt(args[1], 40)  : 40;
        double centerX = args.length > 2 ? parseDouble(args[2], -0.75) : -0.75;
        double centerY = args.length > 3 ? parseDouble(args[3], 0.0)   : 0.0;
        double zoom    = args.length > 4 ? parseDouble(args[4], 1.5)   : 1.5;
        int maxIter    = args.length > 5 ? parseInt(args[5], 200)      : 200;

        printHeader(width, height, centerX, centerY, zoom, maxIter);
        renderMandelbrot(width, height, centerX, centerY, zoom, maxIter);
        printFooter();
    }

    private static void renderMandelbrot(int width, int height, double cx, double cy, double zoom, int maxIter) {
        double aspect = (double)width / (double)height;
        double scaleX = zoom;
        double scaleY = zoom * (aspect / 2.0);

        StringBuilder sb = new StringBuilder(height * (width + 1));
        for (int y = 0; y < height; y++) {
            double py = cy + ((y - height / 2.0) / (height / 2.0)) * scaleY;
            for (int x = 0; x < width; x++) {
                double px = cx + ((x - width  / 2.0) / (width  / 2.0)) * scaleX;
                int it = mandelbrotIterations(px, py, maxIter);
                sb.append(iterToChar(it, maxIter));
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }

    private static int mandelbrotIterations(double cx, double cy, int maxIter) {
        double zx = 0.0, zy = 0.0;
        double zx2 = 0.0, zy2 = 0.0;
        int iter = 0;
        while (zx2 + zy2 <= 4.0 && iter < maxIter) {
            zy = 2.0 * zx * zy + cy;
            zx = zx2 - zy2 + cx;
            zx2 = zx * zx;
            zy2 = zy * zy;
            iter++;
        }
        return iter;
    }

    private static char iterToChar(int iter, int maxIter) {
        if (iter >= maxIter) return PALETTE[0];
        int idx = (int) Math.round(((double) iter / (double) maxIter) * (PALETTE.length - 1));
        idx = Math.min(Math.max(idx, 1), PALETTE.length - 1);
        return PALETTE[idx];
    }

    private static void printHeader(int w, int h, double cx, double cy, double zoom, int it) {
        String ts = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US));
        System.out.println("┌─────────────────────────────────────────────────────────┐");
        System.out.printf ("│  Mandelbrot ASCII  | %s%n", padRight(ts + " (local)", 33) + "│");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        System.out.printf ("│  Size: %-8s  Center: (%.5f, %.5f)%n", (w + "x" + h), cx, cy);
        System.out.printf ("│  Zoom: %-8.3f  Max Iter: %d%n", zoom, it);
        System.out.println("└─────────────────────────────────────────────────────────┘\n");
    }

    private static void printFooter() {
        System.out.println("\nTip: pass args: <width> <height> <centerX> <centerY> <zoom> <maxIter>");
        System.out.println("Ex : java MandelbrotAscii 140 45 -0.743643 -0.131825 0.002 1000");
    }

    private static int parseInt(String s, int def) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return def; }
    }
    private static double parseDouble(String s, double def) {
        try { return Double.parseDouble(s.trim()); } catch (Exception e) { return def; }
    }
    private static String padRight(String s, int n) {
        if (s.length() >= n) return s.substring(0, n);
        return s + " ".repeat(n - s.length());
    }
}