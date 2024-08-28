import { Button } from '@/components/ui/button';
import {
  Sheet,
  SheetContent,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from '@/components/ui/sheet';
import { Avatar, AvatarFallback, AvatarImage } from '@radix-ui/react-avatar';
import {
  DragHandleHorizontalIcon,
  MagnifyingGlassIcon,
} from '@radix-ui/react-icons';
import Sidebar from './Sidebar';

function Navbar() {
  return (
    <div className="px-2 py-3 border-b z-50 bg-background bg-opacity-0 sticky top-0 left-0 right-0 flex justify-between items-center">
      <div className="flex items-center gap-3">
        <Sheet>
          <SheetTrigger>
            <Button
              variant="ghost"
              size="icon"
              className="rounded-full h-11 w-11"
            >
              <DragHandleHorizontalIcon className="h-7 w-7" />
            </Button>
          </SheetTrigger>
          <SheetContent
            side="left"
            className="w-72 border-r-0 flex flex-col justify-center"
          >
            <SheetHeader>
              <SheetTitle className="flex flex-col items-center space-y-2">
                <Avatar className="h-16 w-16">
                  <AvatarImage src="../../../../public/crypto.png" />
                </Avatar>
                <div className="text-center">
                  <span className="font-bold text-green-500 text-xl blink">
                    Intelli
                  </span>
                  <span className="text-xl text-white">Crypto</span>
                  <span className="font-bold text-green-500 text-xl blink">
                    Trade
                  </span>
                </div>
              </SheetTitle>
            </SheetHeader>
            <Sidebar />
          </SheetContent>
        </Sheet>
        <p className="text-sm lg:text-base cursor-pointer font-semibold text-white transition-all duration-300 hover:text-green-500">
          <span className="blink text-green-500">Intelli</span>
          <span className="text-lg font-bold text-white">Crypto</span>
          <span className="blink text-green-500">Trade</span>
        </p>
        <div className="p-0 ml-9">
          <Button variant="outline" className="flex items-center gap-3">
            <MagnifyingGlassIcon />
            <span>Search</span>
          </Button>
        </div>
      </div>
      <div>
        <Avatar>
          <AvatarFallback>P</AvatarFallback>
        </Avatar>
      </div>
    </div>
  );
}

export default Navbar;
