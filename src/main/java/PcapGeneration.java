import org.pcap4j.core.PcapDumper;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.packet.IpPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.UdpPacket;
import org.pcap4j.util.MacAddress;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class PcapGeneration {

  public static void main(String[] args) throws UnknownHostException {
    // Create PCAP file for writing
    String pcapFile = "example.pcap";
    PcapDumper dumper = PcapHandle.openOffline(pcapFile).dumpOpen();

    // Create and write packets
    InetAddress srcAddr = Inet4Address.getByName("192.168.1.1");
    InetAddress dstAddr = Inet4Address.getByName("192.168.1.2");
    MacAddress srcMac = MacAddress.getByName("00:11:22:33:44:55");
    MacAddress dstMac = MacAddress.getByName("AA:BB:CC:DD:EE:FF");
    int srcPort = 1234;
    int dstPort = 5678;

    Packet packet1 = IpPacket.builder()
        .version(4)
        .tos(IpV4Packet.IpV4Tos.DIFFSERV_CS0)
        .identification((short) 100)
        .ttl((byte) 64)
        .protocol(IpNumber.UDP)
        .srcAddr(srcAddr)
        .dstAddr(dstAddr)
        .payloadBuilder(UdpPacket.builder()
            .srcPort(srcPort)
            .dstPort(dstPort)
            .payloadBuilder(new UnknownPacket.Builder().rawData(new byte[] {1, 2, 3, 4})))
        .build();
    dumper.dump(packet1);

    Packet packet2 = IpPacket.builder()
        .version(4)
        .tos(IpV4Packet.IpV4Tos.DIFFSERV_CS0)
        .identification((short) 101)
        .ttl((byte) 64)
        .protocol(IpNumber.UDP)
        .srcAddr(dstAddr)
        .dstAddr(srcAddr)
        .payloadBuilder(UdpPacket.builder()
            .srcPort(dstPort)
            .dstPort(srcPort)
            .payloadBuilder(new UnknownPacket.Builder().rawData(new byte[] {5, 6, 7, 8})))
        .build();
    dumper.dump(packet2);

    // Close the PCAP file
    dumper.close();
  }
}

