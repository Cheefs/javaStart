import javax.sound.midi.*;

public class MiniMidiMusicApp {
    public static void main(String[] args) {
        MiniMidiMusicApp mini = new MiniMidiMusicApp();
        mini.play();
    }

    public void play() {
        try {
            Sequencer player = MidiSystem.getSequencer();
            player.open();

            Sequence sequence = new Sequence( Sequence.PPQ, 4 );

            Track track = sequence.createTrack();
            ShortMessage a = new ShortMessage();
            a.setMessage(144,1,44,100);
            MidiEvent noteOn = new MidiEvent(a,1);
            track.add( noteOn );

            ShortMessage b = new ShortMessage();
            a.setMessage(128,1,44,100);

            MidiEvent noteOff = new MidiEvent( b, 16 );
            track.add( noteOff );

            player.setSequence( sequence );
            player.start();
        } catch ( MidiUnavailableException ex ) {
            ex.printStackTrace();
            System.out.println( "MidiUnavailableException" );
        } catch ( InvalidMidiDataException ex ) {
            ex.printStackTrace();
            System.out.println("InvalidMidiDataException");
        }
    }
}
