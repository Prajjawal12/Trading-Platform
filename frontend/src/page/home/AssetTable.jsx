import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import { Avatar, AvatarImage } from '@radix-ui/react-avatar';

const dummyData = Array(10).fill({
  coin: 'BitCoin',
  symbol: 'BTC',
  volume: '9,528,835,916',
  marketCap: '9,528,835,916,151,561',
  twentyFourH: '0.200009',
  price: '$69,429',
});

function AssetTable() {
  return (
    <div className="overflow-x-auto">
      <Table className="min-w-full">
        <TableHeader>
          <TableRow>
            <TableHead className="text-center">Coin</TableHead>
            <TableHead className="text-center">Symbol</TableHead>
            <TableHead className="text-center">Volume</TableHead>
            <TableHead className="text-center">Market Cap</TableHead>
            <TableHead className="text-center">24H</TableHead>
            <TableHead className="text-center">Price</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {dummyData.map((item, index) => (
            <TableRow key={index}>
              <TableCell className="flex items-center gap-2">
                <Avatar className="h-7 w-7">
                  <AvatarImage src="https://cdn-icons-png.flaticon.com/128/1490/1490849.png" />
                </Avatar>
                <span>{item.coin}</span>
              </TableCell>
              <TableCell className="text-center">{item.symbol}</TableCell>
              <TableCell className="text-center">{item.volume}</TableCell>
              <TableCell className="text-center">{item.marketCap}</TableCell>
              <TableCell className="text-center">{item.twentyFourH}</TableCell>
              <TableCell className="text-center">{item.price}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
}

export default AssetTable;
