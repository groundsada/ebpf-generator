import org.pcap4j.core.PcapHandle;
import org.pcap4j.packet.Packet;
import org.pcap4j.util.ByteArrays;

import java.io.IOException;

public class PcapFiltering {

  public static void main(String[] args) throws IOException {
    // Open PCAP file for reading
    String pcapFile = "example.pcap";
    PcapHandle handle = PcapHandle.openOffline(pcapFile);

    // Compile BPF bytecode
    byte[] bpfCode = ByteArrays.toByteArray("tcp and dst port 80");
    handle.setFilter(ByteBuffer.wrap(bpfCode), BpfCompileMode.OPTIMIZE);

    // Read and filter packets
    Packet packet;
    while ((packet = handle.getNextPacket()) != null) {
      System.out.println(packet);
    }

    // Close the PCAP file
    handle.close();
  }
}

