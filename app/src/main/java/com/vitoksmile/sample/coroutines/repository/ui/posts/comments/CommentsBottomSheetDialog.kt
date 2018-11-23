package com.vitoksmile.sample.coroutines.repository.ui.posts.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vitoksmile.sample.coroutines.repository.R
import com.vitoksmile.sample.coroutines.repository.data.models.Comment
import com.vitoksmile.sample.coroutines.repository.extensions.args
import kotlinx.android.synthetic.main.sheet_comments.*

class CommentsBottomSheetDialog : BottomSheetDialogFragment() {

    companion object {

        private const val KEY_COMMENTS = "KEY_COMMENTS"

        fun show(fragmentManager: FragmentManager, comments: List<Comment>) {
            CommentsBottomSheetDialog().apply {
                arguments = Bundle().apply {
                    putParcelableArray(KEY_COMMENTS, comments.toTypedArray())
                }

                show(fragmentManager, "CommentsBottomSheetDialog")
            }
        }
    }

    private val comments: Array<Comment> by args(KEY_COMMENTS)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View? {
        return inflater.inflate(R.layout.sheet_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setNavigationOnClickListener {
            dismissAllowingStateLoss()
        }

        recyclerView.adapter = CommentsAdapter(comments.toList())
        context?.let {
            recyclerView.addItemDecoration(DividerItemDecoration(it, DividerItemDecoration.VERTICAL))
        }
    }
}