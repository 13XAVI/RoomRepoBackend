package com.rra.meetingRoomMgt.Controller;

import com.rra.meetingRoomMgt.Service.AuthorityService;
import com.rra.meetingRoomMgt.modal.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/rra/v1/admin/roles")
@RequiredArgsConstructor
public class AuthorityController {

    private final AuthorityService authorityService;


    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody Authority authority) {
        Object SavedRole  =  authorityService.saveRoles(authority);
        return ResponseEntity.ok(Map.of("msg", "role created successfuly", "role", SavedRole));
    }

    @GetMapping(path = "/listall")
    public ResponseEntity<List<Authority>> retrieveAutorities() {
        List<Authority> authority = authorityService.retrieveRoles();
        return new ResponseEntity<>(authority, HttpStatus.OK);
    }


    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Authority authority) {
        Object UpdatedRole  =  authorityService.updateRoles(authority);
        return ResponseEntity.ok(Map.of("msg", "role Updated successfuly", "role", UpdatedRole));
    }

    @PutMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody Authority deleteRole) {
        int id = deleteRole.getAuthorityNo();
        int newStatus = deleteRole.getStatus();

        authorityService.deleteRoles(id, newStatus);
        return ResponseEntity.ok(Map.of("msg", "role Deleted successfuly", "id", id));
    }



}
