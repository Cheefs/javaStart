import javax.swing.*;
import javax.sound.midi.*;
import java.awt.*;

public class MiniMusicPlayerGui {
    static JFrame f = new JFrame("");
    static MyDrawPanel ml;

    public static void main(String[] args ) {
        MiniMusicPlayerGui m = new MiniMusicPlayerGui();
        m.go();
    }

    public MidiEvent makeEvent( int cmd, int chan, int one, int two, int tick ) {
        MidiEvent event = null;

        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(cmd, chan, one, two);
            event = new MidiEvent( a, tick );
        } catch (Exception ex ) {
            ex.printStackTrace();
        }
        return event;
    }

    public void go() {
        setUpGui();

        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addControllerEventListener( ml, new int[] { 127 } );
            Sequence seq = new Sequence( Sequence.PPQ, 4 );
            Track track = seq.createTrack();

            for ( int i = 0; i < 60; i += 4 ) {
                int r = (int) ( (Math.random() * 50 ) + 1 );

                track.add( makeEvent( 144, 1, r, 100, i ) );
                track.add( makeEvent( 176, 1, 127, 0, i ) );
                track.add( makeEvent( 128, 1, r, 100, i + 2) );
            }
            sequencer.setSequence(seq);
            sequencer.start();
            sequencer.setTempoInBPM(120);

        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void setUpGui() {
        ml = new MyDrawPanel();
        f.setContentPane( ml );
        f.setBounds(30,30,300,300);
        f.setVisible( true );
    }

    class MyDrawPanel extends JPanel implements  ControllerEventListener {
        boolean msg;

        @Override
        public void controlChange(ShortMessage event) {
            msg = true;
            repaint();
        }

        public void paintComponent(Graphics g) {
            if ( msg ) {
//               Graphics2D g2d = (Graphics2D) g;

               int r = (int) (Math.random() * 250);
               int gr = (int) (Math.random() * 250);
               int b  = (int) (Math.random() * 250);

               g.setColor( new Color(r, gr, b ));

               int ht = (int) (Math.random() * 120 ) + 10;
               int wt = (int) (Math.random() * 120 ) + 10;

               int x = (int) (Math.random() * 40 ) + 10;
               int y = (int) (Math.random() * 40 ) + 10;

               g.fillRect(x, y, ht, wt);
               msg = false;
            }
        }
    }

}

//public class Gui {
//    public static void main(String[] args ) {
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE);
//
//        frame.setSize(200, 200);
//        frame.setVisible( true );
//    }
//}
