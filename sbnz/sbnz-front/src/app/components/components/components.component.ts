import { Component, OnInit } from '@angular/core';
import { MedicalComponent } from '../../model/medicalComponent';
import { ComponentService } from '../../services/component/component.service';


@Component({
  selector: 'app-components',
  templateUrl: './components.component.html',
  styleUrls: ['./components.component.css']
})
export class ComponentsComponent implements OnInit {

  addNew : Boolean = false;

  list: MedicalComponent[];

  component: MedicalComponent = {
    name: ""
  }

  constructor(private componentService: ComponentService) { }

  ngOnInit() {
    this.getAll();
  }

  getAll() {
    this.componentService.getAllComponents().subscribe(data => {
      this.list = data;
    });
  }

  addComponent() {
    this.componentService.createComponent(this.component).subscribe(data =>
      this.getAll());
  }

  deleteComponent(id : number, index : number) {
    console.log(id);
    this.componentService.deleteComponent(id).subscribe(data =>
    this.list.splice(index, 1));
  }

}
