import {Component, OnInit, ViewChild} from '@angular/core';
import {BasicTableData} from "../basic/basic-table/basic-table-data";
import {MatPaginator} from "@angular/material/paginator";
import {MatTable, MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {Participant} from "../models/participant";
import {MatDialog} from "@angular/material/dialog";
import {MessageService} from "../services/message.service";
import {ParticipantService} from "../services/participant.service";
import {SelectionModel} from "@angular/cdk/collections";
import {Action, ParticipantDialogBoxComponent} from "./participant-dialog-box/participant-dialog-box.component";

@Component({
  selector: 'app-participant-list',
  templateUrl: './participant-list.component.html',
  styleUrls: ['./participant-list.component.scss']
})
export class ParticipantListComponent implements OnInit {

  basicTableData: BasicTableData<Participant> = new BasicTableData<Participant>();

  @ViewChild(MatPaginator, {static: true}) paginator!: MatPaginator;
  @ViewChild(MatTable, {static: true}) table: MatTable<any>;
  @ViewChild(MatSort, {static: true}) sort!: MatSort;

  constructor(private ParticipantService: ParticipantService, public dialog: MatDialog, private messageService: MessageService) {
    this.basicTableData.columns = ['id', 'idCard', 'name', 'lastname', 'club'];
    this.basicTableData.columnsTags = ['idHeader', 'idCardHeader', 'nameHeader', 'lastnameHeader', 'clubHeader'];
    this.basicTableData.visibleColumns = ['name', 'lastname', 'club'];
    this.basicTableData.selection = new SelectionModel<Participant>(false, []);
    this.basicTableData.dataSource = new MatTableDataSource<Participant>();
  }

  ngOnInit(): void {
    this.showAllElements();
  }

  showAllElements(): void {
    this.ParticipantService.getAll().subscribe(Participants => {
      this.basicTableData.dataSource.data = Participants;
    });
  }

  addElement(): void {
    const participant = new Participant();
    this.openDialog('Add a new club', Action.Add, participant);
  }

  editElement(): void {
    if (this.basicTableData.selectedElement) {
      this.openDialog('Edit club', Action.Update, this.basicTableData.selectedElement);
    }
  }

  deleteElement(): void {
    if (this.basicTableData.selectedElement) {
      this.openDialog('Delete club', Action.Delete, this.basicTableData.selectedElement);
    }
  }

  setSelectedItem(row: Participant): void {
    if (row === this.basicTableData.selectedElement) {
      this.basicTableData.selectedElement = undefined;
    } else {
      this.basicTableData.selectedElement = row;
    }
  }

  openDialog(title: string, action: Action, participant: Participant) {
    const dialogRef = this.dialog.open(ParticipantDialogBoxComponent, {
      width: '250px',
      data: {title: title, action: action, entity: participant}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result.action == Action.Add) {
        this.addRowData(result.data);
      } else if (result.action == Action.Update) {
        this.updateRowData(result.data);
      } else if (result.action == Action.Delete) {
        this.deleteRowData(result.data);
      }
    });
  }

  addRowData(participant: Participant) {
    this.ParticipantService.add(participant).subscribe(participant => {
      this.basicTableData.dataSource.data.push(participant);
      this.basicTableData.dataSource._updateChangeSubscription();
      this.messageService.infoMessage("clubStored");
    });
  }

  updateRowData(participant: Participant) {
    this.ParticipantService.update(participant).subscribe(() => {
        this.messageService.infoMessage("clubUpdated");
      }
    );
  }

  deleteRowData(participant: Participant) {
    this.ParticipantService.delete(participant).subscribe(() => {
        this.basicTableData.dataSource.data = this.basicTableData.dataSource.data.filter(existing_Participant => existing_Participant !== participant);
        this.messageService.infoMessage("clubDeleted");
      }
    );
  }

}
