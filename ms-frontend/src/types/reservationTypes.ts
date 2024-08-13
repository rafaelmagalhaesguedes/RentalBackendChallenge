export type CheckInType = {
  id: number;
  location: string;
  checkInDate: string;
  checkInTime: string;
  checkOutDate: string;
  checkOutTime: string;
};

export type GroupType = {
  id: number;
  name: string;
  vehicles: string;
  dailyRate: number;
  imageURL: string;
};

export type AdditionalType = {
  id: number;
  name: string;
  description: string;
  dailyRate: number;
  quantity: number;
};

export type ReservationType = {
  accessoryIds: number[];
  groupId: number;
  paymentType: string;
  personId: number;
  pickupDateTime: string;
  returnDateTime: string;
  totalAmount: number;
};