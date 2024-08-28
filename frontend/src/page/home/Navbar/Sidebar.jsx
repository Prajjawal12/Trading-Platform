import { Button } from '@/components/ui/button';
import { SheetClose } from '@/components/ui/sheet';
import {
  ActivityLogIcon,
  BookmarkIcon,
  DashboardIcon,
  HomeIcon,
  PersonIcon,
  ExitIcon,
} from '@radix-ui/react-icons';
import { CreditCardIcon, LandmarkIcon, WalletIcon } from 'lucide-react';

const menu = [
  {
    name: 'Home',
    path: '/',
    icon: <HomeIcon className="h-6 w-6" />,
  },
  {
    name: 'Portfolio',
    path: '/portfolio',
    icon: <DashboardIcon className="h-6 w-6" />,
  },
  {
    name: 'Watchlist',
    path: '/watchlist',
    icon: <BookmarkIcon className="h-6 w-6" />,
  },
  {
    name: 'Activity',
    path: '/activity',
    icon: <ActivityLogIcon className="h-6 w-6" />,
  },
  {
    name: 'Wallet',
    path: '/wallet',
    icon: <WalletIcon className="h-6 w-6" />,
  },
  {
    name: 'Payment Details',
    path: '/payment-details',
    icon: <LandmarkIcon className="h-6 w-6" />,
  },
  {
    name: 'Withdrawal',
    path: '/withdrawal',
    icon: <CreditCardIcon className="h-6 w-6" />,
  },

  {
    name: 'Profile',
    path: '/profile',
    icon: <PersonIcon className="h-6 w-6" />,
  },
  {
    name: 'Logout',
    path: '/profile',
    icon: <ExitIcon className="h-6 w-6" />,
  },
];

function Sidebar() {
  return (
    <div className="mt-10 space-y-5 overflow-y-auto scrollbar-hide">
      {menu.map((item) => (
        <div key={item.name}>
          <SheetClose className="w-full">
            <Button
              variant="outline"
              className="flex items-center gap-4 py-3 w-full text-left"
            >
              <span className="w-8 flex justify-center">{item.icon}</span>
              <p className="flex-1">{item.name}</p>
            </Button>
          </SheetClose>
        </div>
      ))}
    </div>
  );
}

export default Sidebar;
