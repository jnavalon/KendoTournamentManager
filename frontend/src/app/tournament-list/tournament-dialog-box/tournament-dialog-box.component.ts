import {Component, Inject, OnInit, Optional} from '@angular/core';
import {FormControl} from "@angular/forms";
import {Tournament} from "../../models/tournament";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

export enum Action {
  Add,
  Update,
  Delete,
  Cancel
}

@Component({
  selector: 'app-tournament-dialog-box',
  templateUrl: './tournament-dialog-box.component.html',
  styleUrls: ['./tournament-dialog-box.component.scss']
})
export class TournamentDialogBoxComponent {

  tournament: Tournament;
  title: string;
  action: Action;
  actionName: string;

  constructor(
    public dialogRef: MatDialogRef<TournamentDialogBoxComponent>,
    //@Optional() is used to prevent error if no data is passed
    @Optional() @Inject(MAT_DIALOG_DATA) public data: { title: string, action: Action, entity: Tournament }) {
    this.tournament = data.entity;
    this.title = data.title;
    this.action = data.action;
    this.actionName = Action[data.action];
  }

  doAction() {
    this.dialogRef.close({data: this.tournament, action: this.action});
  }

  closeDialog() {
    this.dialogRef.close({action: Action.Cancel});
  }

}
