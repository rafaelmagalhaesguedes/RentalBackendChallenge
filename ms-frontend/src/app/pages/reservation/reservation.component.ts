// reservation.component.ts
import { Component, OnInit } from '@angular/core';
import { ReservationService } from '../../../services/reservation.service';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {
  groups: any[] = [];
  accessories: any[] = [];
  selectedGroup: any;
  selectedAccessories: any[] = [];
  pickupDate: any;
  returnDate: any;
  total: number = 0;

  constructor(private reservationService: ReservationService) {}

  ngOnInit(): void {
    this.reservationService.getGroups().subscribe((data) => this.groups = data);
    this.reservationService.getAccessories().subscribe((data) => this.accessories = data);
  }

  calculateTotal(): void {
    const days = (new Date(this.returnDate).getTime() - new Date(this.pickupDate).getTime()) / (1000 * 3600 * 24);
    let total = days * this.selectedGroup.price;
    this.selectedAccessories.forEach(accessory => {
      total += days * accessory.price;
    });
    this.total = total;
  }

  onAccessoryChange(event: any): void {
    const accessory = event.target.value;
    if (event.target.checked) {
      this.selectedAccessories.push(accessory);
    } else {
      const index = this.selectedAccessories.indexOf(accessory);
      if (index > -1) {
        this.selectedAccessories.splice(index, 1);
      }
    }
  }
}